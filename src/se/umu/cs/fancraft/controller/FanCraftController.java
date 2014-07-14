package se.umu.cs.fancraft.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.umu.cs.fancraft.dao.CommentDAO;
import se.umu.cs.fancraft.dao.CraftDAO;
import se.umu.cs.fancraft.dao.FandomDAO;
import se.umu.cs.fancraft.dao.PostDAO;
import se.umu.cs.fancraft.dao.S3DAO;
import se.umu.cs.fancraft.dao.UserDAO;
import se.umu.cs.fancraft.entity.Comment;
import se.umu.cs.fancraft.entity.Post;
import se.umu.cs.fancraft.entity.User;
import se.umu.cs.fancraft.form.AddCommentForm;
import se.umu.cs.fancraft.form.AddCraftForm;
import se.umu.cs.fancraft.form.AddFandomForm;
import se.umu.cs.fancraft.form.AddFriendForm;
import se.umu.cs.fancraft.form.CommentDisplay;
import se.umu.cs.fancraft.form.LoginForm;
import se.umu.cs.fancraft.form.PostDisplay;
import se.umu.cs.fancraft.form.PostForm;
import se.umu.cs.fancraft.form.RegisterForm;
import se.umu.cs.fancraft.form.ShareForm;
import se.umu.cs.fancraft.util.Friend;
import se.umu.cs.fancraft.util.UserDetails;

@Controller
public class FanCraftController {

	private static int DEFAULT_TIME_WINDOW = 5;

	@Autowired
	private UserDetails userDetails;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private CraftDAO craftDAO;

	@Autowired
	private FandomDAO fandomDAO;

	@Autowired
	private S3DAO s3DAO;

	@RequestMapping("login.do")
	public ModelAndView showLogin() {
		userDetails.setUserId(null);
		userDetails.setDisplayName(null);
		userDetails.setCrafts(null);
		userDetails.setFandoms(null);
		userDetails.setFriends(null);
		userDetails.setDisplayName(null);
		LoginForm loginForm = new LoginForm();
		ModelAndView mav = new ModelAndView("login", "loginForm", loginForm);
		return mav;
	}

	@RequestMapping("performlogin.do")
	public ModelAndView performLogin(@Valid LoginForm loginForm,
			BindingResult result, Map<String, Object> model) {
		LoginForm lf = (LoginForm) model.get("loginForm");
		if (lf != null && lf.getUserName() != null && !lf.getUserName().isEmpty()) {
			User u = userDAO.getUser(lf.getUserName());
			if (u != null) {
				if (lf.getPassword().equals(u.getPass())) {
					userDetails.setUserId(lf.getUserName());
					userDetails.setDisplayName(u.getName());
					userDetails.setFriends(createFriendsList(u.getFriends()));
					userDetails.setCrafts(setToList(u.getCrafts()));
					userDetails.setFandoms(setToList(u.getFandoms()));
					return showNewsfeed();
				}
			}
		}
		return showLogin();
	}

	@RequestMapping("addcomment.do")
	public ModelAndView addComment(@Valid AddCommentForm addCommentForm,
			BindingResult result, Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();

		commentDAO.addComment(addCommentForm.getPosterID(),
				addCommentForm.getPostTimestamp(), userDetails.getUserId(),
				addCommentForm.getText());

		return showNewsfeed();
	}

	@RequestMapping(value = "sharepost.do")
	public ModelAndView sharePost(@Valid ShareForm shareForm,
			BindingResult result, Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();

		Post p = new Post();
		p.setPosterId(userDetails.getUserId());
		p.setPosttimestamp(dateToString(new Date()));
		p.setPostText(shareForm.getShareText());
		p.setOriginalPostId(shareForm.getPosterId());
		p.setOriginalPostTimestamp(shareForm.getPostTimestamp());

		// fetch shared post and add the file links to the new one
		Post s = postDAO.getPost(shareForm.getPosterId(),
				shareForm.getPostTimestamp());

		if (s != null) {
			p.setFileLink(s.getFileLink());
			p.setFileName(s.getFileName());
			p.setPictureLink(s.getPictureLink());
			p.setCraftId(s.getCraftId());
			p.setFandomId(s.getFandomId());

			postDAO.savePost(p);

			if (s.getShares() == null)
				s.setShares(new HashSet<String>());

			// increase the amount of shares on the original post
			s.getShares().add(userDetails.getUserId());

			postDAO.savePost(s);
		}

		return showNewsfeed();
	}

	@RequestMapping(value = "likepost.do", params = { "id", "timestamp" })
	public ModelAndView likePost(@RequestParam(value = "id") String posterID,
			@RequestParam(value = "timestamp") String postTimestamp) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();

		Post p = postDAO.getPost(posterID, postTimestamp);
		if (p == null)
			return showNewsfeed();

		Set<String> likes = p.getLikes();
		if (likes == null) {
			likes = new HashSet<String>();
			p.setLikes(likes);
		}

		if (!p.getLikes().contains(userDetails.getUserId())) {
			p.getLikes().add(userDetails.getUserId());
			postDAO.savePost(p);
		}

		return showNewsfeed();
	}

	@RequestMapping(value = "unlikepost.do", params = { "id", "timestamp" })
	public ModelAndView unlikePost(@RequestParam(value = "id") String posterID,
			@RequestParam(value = "timestamp") String postTimestamp) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();

		Post p = postDAO.getPost(posterID, postTimestamp);
		if (p == null)
			return showNewsfeed();

		if (p.getLikes() != null) {
			if (p.getLikes().contains(userDetails.getUserId())) {
				p.getLikes().remove(userDetails.getUserId());
				
				if(p.getLikes().size() == 0)
					p.setLikes(null);
				
				postDAO.savePost(p);
			}
		}



		return showNewsfeed();
	}
	
	@RequestMapping("logout.do")
	public ModelAndView performLogout() {
		return showLogin();
	}

	@RequestMapping("register.do")
	public ModelAndView showRegister() {
		RegisterForm registerForm = new RegisterForm();
		ModelAndView mav = new ModelAndView("register", "registerForm",
				registerForm);
		return mav;
	}

	@RequestMapping("performregister.do")
	public ModelAndView performRegister(@Valid RegisterForm registerForm,
			BindingResult result, Map<String, Object> model) {		
		RegisterForm rf = (RegisterForm) model.get("registerForm");
		if (rf != null && rf.getUserName() != null) {
			if (!userDAO.exists(rf.getUserName())) {
				userDAO.saveUser(rf.getUserName(), rf.getPassword(),
						rf.getDisplayName());
				return showLogin();
			}
		}
		return new ModelAndView("register");
	}

	@RequestMapping("addfriend.do")
	public ModelAndView addFriend(@Valid AddFriendForm addFriendForm,
			BindingResult result, Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		AddFriendForm ff = addFriendForm;
		if (ff != null) {
			User newFriend = userDAO.addFriend(userDetails.getUserId(),
					ff.getFriendName());
			if (newFriend != null) {
				// the friend is in the DB, but no in the java code yet
				if (userDetails.getFriends() == null)
					userDetails.setFriends(new ArrayList<Friend>());
				userDetails.getFriends().add(
						new Friend(newFriend.getId(), newFriend.getName()));
			}
		}
		return showNewsfeed();
	}

	@RequestMapping("newsfeed.do")
	public ModelAndView showNewsfeed() {
		// check if user is logged in, if not go back to login
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		else {
			// get user's and user's friend's posts
			ArrayList<PostDisplay> postsToShow = getPostTimeline();

			// and show everything
			ModelAndView mav = loadNewsFeedModel(postsToShow);

			return mav;
		}
	}

	@RequestMapping(value = "userposts.do", params = { "userID" })
	public ModelAndView showUserPosts(
			@RequestParam(value = "userID") String userID) {
		// check if user is logged in, if not go back to login
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		else {
			// get user's and user's friend's posts
			ArrayList<PostDisplay> postsToShow = getPostByUser(userID);

			// and show everything
			ModelAndView mav = loadNewsFeedModel(postsToShow);

			return mav;
		}
	}

	@RequestMapping(value = "fandomposts.do", params = { "fandomID" })
	public ModelAndView showFandomPosts(
			@RequestParam(value = "fandomID") String fandomID) {
		// check if user is logged in, if not go back to login
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		else {
			// get user's and user's friend's posts
			ArrayList<PostDisplay> postsToShow = getPostByFandom(fandomID);

			// and show everything
			ModelAndView mav = loadNewsFeedModel(postsToShow);

			return mav;
		}
	}

	@RequestMapping(value = "craftposts.do", params = { "craftID" })
	public ModelAndView showCraftPosts(
			@RequestParam(value = "craftID") String craftID) {
		// check if user is logged in, if not go back to login
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		else {
			// get user's and user's friend's posts
			ArrayList<PostDisplay> postsToShow = getPostByCraft(craftID);

			// and show everything
			ModelAndView mav = loadNewsFeedModel(postsToShow);

			return mav;
		}
	}

	@RequestMapping("addcraft.do")
	public ModelAndView addCraft(AddCraftForm addCraftForm,
			Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		String craft = addCraftForm.getCraftId();
		if (userDetails.getCrafts() == null)
			userDetails.setCrafts(new ArrayList<String>());
		List<String> myCrafts = userDetails.getCrafts();
		if (!myCrafts.contains(craft)) {
			boolean success = userDAO.addCraft(userDetails.getUserId(), craft);
			if (success)
				userDetails.getCrafts().add(craft);
		}
		return showNewsfeed();
	}

	@RequestMapping("addfandom.do")
	public ModelAndView addFandom(AddFandomForm addFandomForm,
			Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();
		String fandom = addFandomForm.getFandomId();
		if (userDetails.getFandoms() == null)
			userDetails.setFandoms(new ArrayList<String>());
		List<String> myFandoms = userDetails.getFandoms();
		if (!myFandoms.contains(fandom)) {
			boolean success = userDAO
					.addFandom(userDetails.getUserId(), fandom);
			if (success)
				userDetails.getFandoms().add(fandom);
		}
		return showNewsfeed();
	}

	@RequestMapping("post.do")
	public ModelAndView savePost(PostForm postForm, Map<String, Object> model) {
		if (userDetails.getUserId() == null
				|| userDetails.getUserId().isEmpty())
			return showLogin();

		Post post = new Post();
		post.setPosterId(userDetails.getUserId());
		post.setPosttimestamp(dateToString(new Date()));
		post.setPostText(postForm.getText());

		// work on files
		if (postForm.getFile() != null && !postForm.getFile().isEmpty()) {
			String link = s3DAO.storeFile(postForm.getFile(),
					userDetails.getUserId(), post.getPosttimestamp());
			post.setFileLink(link);
			post.setFileName(postForm.getFile().getOriginalFilename());
		}
		// do the same for the picture
		if (postForm.getPicture() != null && !postForm.getPicture().isEmpty()) {
			String link = s3DAO.storePicture(postForm.getPicture(),
					userDetails.getUserId(), post.getPosttimestamp());
			post.setPictureLink(link);
		}

		post.setCraftId(postForm.getCraftId());
		post.setFandomId(postForm.getFandomId());

		postDAO.savePost(post);
		return showNewsfeed();
	}

	private ModelAndView loadNewsFeedModel(List<PostDisplay> postList) {
		ModelAndView mav = new ModelAndView("newsfeed");
		mav.addObject("posts", postList);
		mav.addObject("userDetails", userDetails);
		mav.addObject("friends", userDetails.getFriends());
		mav.addObject("userCrafts", userDetails.getCrafts());
		mav.addObject("userFandoms", userDetails.getFandoms());
		mav.addObject("crafts", craftDAO.getCraftsMap());
		mav.addObject("fandoms", fandomDAO.getFandomsMap());

		// this is extra
		mav.addObject("postForm", new PostForm());
		mav.addObject("addFriendForm", new AddFriendForm());
		mav.addObject("addFandomForm", new AddFandomForm());
		mav.addObject("addCraftForm", new AddCraftForm());
		mav.addObject("shareForm", new ShareForm());
		mav.addObject("addCommentForm", new AddCommentForm());
		return mav;
	}

	private ArrayList<PostDisplay> getPostByFandom(String fandomID) {
		ArrayList<PostDisplay> postsToShow = new ArrayList<PostDisplay>();
		List<Post> temp = postDAO.getpostsByFandom(fandomID,
				DEFAULT_TIME_WINDOW);
		for (Post p : temp) {
			postsToShow.add(rawToDisplay(p));
		}

		// order posts by timestamp
		Collections.sort(postsToShow, new Comparator<PostDisplay>() {
			public int compare(PostDisplay p1, PostDisplay p2) {
				return -1
						* p1.getPostTimestamp()
								.compareTo(p2.getPostTimestamp());
			}
		});

		return postsToShow;
	}

	private ArrayList<PostDisplay> getPostByCraft(String craftID) {
		ArrayList<PostDisplay> postsToShow = new ArrayList<PostDisplay>();
		List<Post> temp = postDAO.getpostsByCraft(craftID, DEFAULT_TIME_WINDOW);
		for (Post p : temp) {
			postsToShow.add(rawToDisplay(p));
		}
		// order posts by timestamp
		Collections.sort(postsToShow, new Comparator<PostDisplay>() {
			public int compare(PostDisplay p1, PostDisplay p2) {
				return -1
						* p1.getPostTimestamp()
								.compareTo(p2.getPostTimestamp());
			}
		});

		return postsToShow;
	}

	private ArrayList<PostDisplay> getPostByUser(String userID) {
		// get user's and user's friend's posts
		ArrayList<PostDisplay> postsToShow = new ArrayList<PostDisplay>();

		List<Post> temp = postDAO.getpostsByUser(userID, DEFAULT_TIME_WINDOW);

		// convert user posts into something we can display
		for (Post p : temp) {
			postsToShow.add(rawToDisplay(p));
		}
		// order posts by timestamp

		Collections.sort(postsToShow, new Comparator<PostDisplay>() {
			public int compare(PostDisplay p1, PostDisplay p2) {
				return -1
						* p1.getPostTimestamp()
								.compareTo(p2.getPostTimestamp());
			}
		});

		return postsToShow;
	}

	private ArrayList<PostDisplay> getPostTimeline() {
		// get user's and user's friend's posts
		ArrayList<PostDisplay> postsToShow = new ArrayList<PostDisplay>();

		List<Post> temp = postDAO.getpostsByUser(userDetails.getUserId(),
				DEFAULT_TIME_WINDOW);

		// convert user posts into something we can display
		for (Post p : temp) {
			postsToShow.add(rawToDisplay(p));
		}
		// get friends posts too
		if (userDetails.getFriends() != null) {
			for (Friend friend : userDetails.getFriends()) {
				temp = postDAO.getpostsByUser(friend.getUserId(),
						DEFAULT_TIME_WINDOW);
				for (Post p : temp) {
					postsToShow.add(rawToDisplay(p));
				}
			}
		}
		// order posts by timestamp

		Collections.sort(postsToShow, new Comparator<PostDisplay>() {
			public int compare(PostDisplay p1, PostDisplay p2) {
				return -1
						* p1.getPostTimestamp()
								.compareTo(p2.getPostTimestamp());
			}
		});

		return postsToShow;
	}

	/**
	 * Transform a post in something visible when the time arrives
	 * 
	 * @param post
	 * @return
	 */
	private PostDisplay rawToDisplay(Post post) {
		PostDisplay toDisplay = new PostDisplay();
		toDisplay.setPosterId(post.getPosterId());
		toDisplay.setPostTimestamp(stringToDate(post.getPosttimestamp()));
		toDisplay.setShared(false);// a boolean is false by default, but just in
									// case

		// original post part
		if (post.getOriginalPostId() != null
				&& !post.getOriginalPostId().isEmpty()) {
			Post shared = postDAO.getPost(post.getOriginalPostId(),
					post.getOriginalPostTimestamp());

			toDisplay.setOriginalPosterId(shared.getPosterId());
			toDisplay.setOriginalPosterName(userDAO.getDisplayName(shared
					.getPosterId()));
			toDisplay.setOriginalPostTimestamp(stringToDate(shared
					.getPosttimestamp()));
			toDisplay.setOriginalPostText(shared.getPostText());
			toDisplay.setShared(true);// this is necessary!
		}

		toDisplay.setPosterName(userDAO.getDisplayName(post.getPosterId()));
		toDisplay.setText(post.getPostText());
		toDisplay.setFandomId(post.getFandomId());
		toDisplay.setCraftId(post.getCraftId());
		toDisplay.setFileLink(post.getFileLink());
		toDisplay.setFileLabel(post.getFileName());
		toDisplay.setPictureLink(post.getPictureLink());

		// get comments for this post
		List<Comment> cl = commentDAO.getCommentsForPost(post.getPosterId(),
												post.getPosttimestamp());

		if (cl != null) {
			ArrayList<CommentDisplay> comments = new ArrayList<CommentDisplay>();

			for (Comment c : cl) {
				CommentDisplay cd = new CommentDisplay();
				cd.setPosterID(c.getPosterId());
				cd.setPosterName(userDAO.getDisplayName(c.getPosterId()));
				cd.setPostTimestamp(stringToDate(c.getCommentTimestamp()));
				cd.setPostText(c.getCommentText());
				comments.add(cd);
			}

			// sort by comment timestamp, ascending
			Collections.sort(comments, new Comparator<CommentDisplay>() {
				public int compare(CommentDisplay p1, CommentDisplay p2) {
					return p1.getPostTimestamp().compareTo(
							p2.getPostTimestamp());
				}
			});

			toDisplay.setComments(comments);

		}

		if (post.getLikes() != null && !post.getLikes().isEmpty()) {

			toDisplay.setLiked(post.getLikes()
					.contains(userDetails.getUserId()) ? true : false);

			StringBuilder sb = new StringBuilder();
			String[] ids = post.getLikes().toArray(new String[0]);
			if (post.getLikes().size() >= 3) {
				// shows names of 3 first persons
				for (int j = 0; j < 3; j++) {
					sb.append(userDAO.getDisplayName(ids[j]));
					sb.append(", ");
				}
				sb.append("and ");
				sb.append(post.getLikes().size() - 3);
				sb.append("  more like this.");
				toDisplay.setLikes(sb.toString());
			} else if (post.getLikes().size() == 2) {
				sb.append(userDAO.getDisplayName(ids[0]));
				sb.append(" and ");
				sb.append(userDAO.getDisplayName(ids[1]));
				sb.append(" like this.");
				toDisplay.setLikes(sb.toString());
			} else // 1 person
			{
				sb.append(userDAO.getDisplayName(ids[0]));
				sb.append(" likes this.");
				toDisplay.setLikes(sb.toString());
			}

		}
		if (post.getShares() != null && !post.getShares().isEmpty()) {
			toDisplay.setShared(post.getShares().contains(
					userDetails.getUserId()) ? true : false);
			toDisplay.setShares(String.valueOf(post.getShares().size())
					+ " shares.");
		}
		return toDisplay;
	}

	private List<String> setToList(Set<String> set) {
		if (set == null)
			return null;

		return new ArrayList<String>(set);
	}

	private List<Friend> createFriendsList(Set<String> set) {
		if (set == null)
			return new ArrayList<Friend>();

		ArrayList<Friend> friends = new ArrayList<Friend>();
		List<String> fr = setToList(set);
		for (String id : fr) {
			Friend f = new Friend();
			f.setUserId(id);
			f.setDisplayName(userDAO.getDisplayName(id));
			friends.add(f);
		}
		return friends;
	}

	private Date stringToDate(String timestamp) {
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date d = new Date();
		try {
			d = df.parse(timestamp);
		} catch (ParseException e) {

		}
		return d;
	}

	private String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(date);
	}

	private Map<String, String> listToMap(List<String> list) {
		if (list == null)
			return null;

		HashMap<String, String> map = new HashMap<String, String>();
		for (String s : list) {
			map.put(s, s);
		}

		return map;
	}

}
