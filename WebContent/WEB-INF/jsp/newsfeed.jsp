<%@page import="se.umu.cs.fancraft.form.CommentDisplay"%>
<%@page import="se.umu.cs.fancraft.form.PostDisplay"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="se.umu.cs.fancraft.entity.Fandom"%>
<%@page import="se.umu.cs.fancraft.util.Friend"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FanCraft: News Feed</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  ================================================== -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<!-- CSS
  ================================================== -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/bootstrap-theme.min.css"
	type="text/css" media="screen">
<link rel="stylesheet" href="css/theme.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/navbar.css" type="text/css"
	media="screen">

<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

</head>
<body style="margin-top: 50px">
	<!-- navbar -->
	<header class="navbar navbar-default navbar-fixed-top" role="banner">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<label class="navbar-brand">FanCraft</label>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="newsfeed.do">${userDetails.displayName}</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout.do">Logout</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</header>

	<div class="container">

		<div class="row">

			<div class="col-md-3">

				<!-- FIRST COLUMN: USER'S FANDOMS AND CRAFTS -->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Add Fandom</h3>
					</div>
					<div class="panel-body">
						<form:form action="addfandom.do" commandName="addFandomForm">
							<form:select path="fandomId" items="${fandoms}" />
							<button type="submit" class="btn btn-primary">Add</button>
						</form:form>
					</div>
				</div>

				<!-- Now I have to load my fandoms and crafts -->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">My Fandoms</h3>
					</div>
					<div class="panel-body">
						<ul class="list-group">

							<c:forEach var="fandom" items="${userFandoms}">
								<li class="list-group-item"><a
									href="<c:url value="fandomposts.do"><c:param name="fandomID" value="${fandom}"/></c:url>">${fandom}</a>
								</li>
							</c:forEach>

						</ul>
					</div>
				</div>

				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Add Craft</h3>
					</div>
					<div class="panel-body">
						<form:form action="addcraft.do" commandName="addCraftForm">
							<form:select path="craftId" items="${crafts}" />
							<button type="submit" class="btn btn-primary">Add</button>
						</form:form>
					</div>
				</div>

				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">My Crafts</h3>
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<c:forEach var="craft" items="${userCrafts}">
								<li class="list-group-item"><a
									href="<c:url value="craftposts.do"><c:param name="craftID" value="${craft}"/></c:url>">${craft}</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>

			</div>

			<!-- SECOND COLUMN (AND MAIN COLUMN!): POSTS -->
			<div class="col-md-6">


				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">New Post</h3>
					</div>
					<div class="panel-body">
						<form:form action="post.do" enctype="multipart/form-data"
							commandName="postForm">
							<form:textarea path="text" rows="10" cols="75"
								cssStyle="width: 500px; height: 150px;" />
							<form:input type="file" path="picture" title="Add photo" accept="image/*"/>
							<form:input type="file" path="file" title="Add file" />
							<table>
								<tr>
									<td><label>Select a craft</label> <form:select
											path="craftId" items="${crafts}" /></td>
									<td><label>Select a fandom</label> <form:select
											path="fandomId" items="${fandoms}" /></td>
								</tr>
							</table>
							<button type="submit" class="btn btn-primary">Post it!</button>
						</form:form>
					</div>
				</div>

				<c:if test="${not empty posts}">

					<div class="panel panel-primary">
						<div class="panel-body">

							<c:forEach var="post" items="${posts}">

								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title"><a
									href="<c:url value="userposts.do"><c:param name="userID" value="${post.posterId}"/></c:url>">${post.posterName}</a> published at
											${post.postTimestamp}</h3>
									</div>
									<div class="panel-body">
										<p>${post.text}</p>

										<!-- show original post if this is a share -->
										<c:if test="${not empty post.originalPosterName}">
											<div class="panel panel-primary">
												<div class="panel-heading">
													<h3 class="panel-title"><a
									href="<c:url value="userposts.do"><c:param name="userID" value="${post.originalPosterId}"/></c:url>">${post.originalPosterName}</a> originally published at ${post.originalPostTimestamp}</h3>
												</div>
												<div class="panel-body">
													<p>${post.originalPostText}</p>
													<c:if test="${not empty post.pictureLink}">
														<p align="center">
															<img src="${post.pictureLink}" width="350px" />
														</p>
													</c:if>
													<c:if test="${not empty post.fileLink}">
														<p>
															<a class="btn btn-primary" href="${post.fileLink}"
																role="button">${post.fileLabel}</a>
														</p>
													</c:if>
												</div>
											</div>

										</c:if>

										<!-- display picture if post has one and isn't a shared post -->
										<c:if test="${empty post.originalPosterName}">
											<c:if
												test="${not empty post.pictureLink}">
												<p align="center">
													<img src="${post.pictureLink}" width="350px" />
												</p>
											</c:if>
	
											<!-- link to file if post has one -->
											<c:if
												test="${not empty post.fileLink}">
												<p>
													<a class="btn btn-primary" href="${post.fileLink}"
														role="button">${post.fileLabel}</a>
												</p>
											</c:if>
										</c:if>

										<!-- post categories -->
										<p align="right">
											<a
												href="<c:url value="fandomposts.do"><c:param name="fandomID" value="${post.fandomId}"/></c:url>">${post.fandomId}</a>
											<a
												href="<c:url value="craftposts.do"><c:param name="craftID" value="${post.craftId}"/></c:url>">${post.craftId}</a>
										</p>

										<!-- likes/unlikes -->
										<!-- Unlike -->
										<p>
											<c:if test="${post.liked}">
												<a
													href="<c:url value="unlikepost.do"><c:param name="id" value="${post.posterId}"/><c:param name="timestamp" value="${post.postTimestampToString}"/></c:url>">Unlike</a>
												<c:if test="${not empty post.likes}">
														 ${post.likes}
													</c:if>
											</c:if>

											<!-- Unlike -->
											<c:if test="${not post.liked}">
												<a
													href="<c:url value="likepost.do"><c:param name="id" value="${post.posterId}"/><c:param name="timestamp" value="${post.postTimestampToString}"/></c:url>">Like</a>
												<c:if test="${not empty post.likes}">
														 ${post.likes}
												</c:if>
											</c:if>
											
											<c:if test="${not empty post.shares}">
													${post.shares}
											</c:if>
										</p>



										<div class="container">
											<div class="five columns">
												<!-- shares -->
												<!-- display share form -->

												<label>Share this with your friends!</label>
												<form:form action="sharepost.do" commandName="shareForm">
													<form:hidden path="posterId" value="${post.posterId}" />
													<form:hidden path="postTimestamp"
														value="${post.postTimestampToString}" />
													<form:textarea path="shareText" rows="10" cols="75"
														cssStyle="width: 400px; height: 45px;" />
													<input type="submit" value="Share" />
												</form:form>

											</div>
										</div>

										<div class="panel-footer">

											<!-- comments -->
											<c:forEach var="comment" items="${post.comments}">
												<div class="panel panel-default">
													<div class="panel-heading">
														<h3 class="panel-title">${comment.posterName} said at
															${comment.postTimestamp}</h3>
													</div>
													<div class="panel-body">
														<p>${comment.postText}</p>
													</div>
												</div>
											</c:forEach>

											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">Add a comment</h3>
												</div>
												<div class="panel-body">

													<!-- add comment -->
													<form:form action="addcomment.do"
														commandName="addCommentForm">
														<form:hidden path="posterID" value="${post.posterId}" />
														<form:hidden path="postTimestamp"
															value="${post.postTimestampToString}" />

														<form:textarea path="text" rows="10" cols="75"
															cssStyle="width: 400px; height: 45px;" />
														<input type="submit" value="Comment" />
													</form:form>
												</div>
											</div>

										</div>




									</div>
								</div>


							</c:forEach>
						</div>
					</div>
				</c:if>

				<c:if test="${empty posts}">
					<p>Oops! It seems you have no posts available at the moment.
						Try following your friends or publishing a post.</p>
				</c:if>



			</div>

			<!-- THIRD COLUMN: USER'S FRIENDS AND ADD FRIENDS -->
			<div class="col-md-3">

				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Add Friend</h3>
					</div>
					<div class="panel-body">
						<form:form action="addfriend.do" commandName="addFriendForm">
							<form:input path="friendName" />
							<button type="submit" class="btn btn-primary">Add</button>
						</form:form>
					</div>
				</div>

				<hr />
				<!-- Loading friends -->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">My Friends</h3>
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<c:forEach var="friend" items="${friends}">
								<li class="list-group-item"><a
									href="<c:url value="userposts.do"><c:param name="userID" value="${friend.userId}"/></c:url>">${friend.displayName}</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>

			</div>



		</div>

	</div>
	<!-- END CONTAINER -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>