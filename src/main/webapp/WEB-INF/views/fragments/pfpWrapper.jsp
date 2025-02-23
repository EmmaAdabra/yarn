<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty sessionScope.sessionUser.pfpUrl}">
        <img class="object-cover w-[100%] h-[100%] rounded-full" src="${sessionScope.sessionUser.pfpUrl}" alt="profile picture">
    </c:when>
    <c:when test="${not empty sessionScope.sessionUser.userInitial}">
        <div class="center-icon p-1 rounded-full">
             <span class="">
                     ${sessionScope.sessionUser.userInitial}
             </span>
        </div>
    </c:when>
    <c:otherwise>
        <img class="object-cover w-[100%] h-[100%] rounded-full" src="<c:url value="/assets/images/logo.png" />" alt="yarn logo">
    </c:otherwise>
</c:choose>