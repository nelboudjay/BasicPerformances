<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test JAVA</title>
</head>
<body>

	<h1>Measurement de performances basiques d’exécution du serveur</h1>
	<c:choose>
		<c:when test="${error == null}">
			<c:out value="${error}"/>
			<div>Le temps d’exécution pour créer le fichier est de <b><c:out value="${createFileTime}"></c:out></b> secondes</div>
			<div>Le temps d’exécution pour copier le fichier 5 fois <b><c:out value="${copyFilesTime}"></c:out></b> secondes</div>
			<div>Le temps d’exécution pour compresser les fichiers produits est de <b><c:out value="${zipFilesTime}"></c:out></b> secondes</div>
			<br/>
			<div>Le temps d’exécution de tous les travaux est de <b><c:out value="${allTime}"></c:out></b> secondes</div>
			<br/>
			<div>Pous pouvez trouver tous les fichers creés dans le chemin suivant: <c:out value="${filesPath}"></c:out></div>
		</c:when>
		<c:otherwise>
			<div><c:out value="${error}"></c:out></div>
		</c:otherwise>
	</c:choose>

</body>
</html>