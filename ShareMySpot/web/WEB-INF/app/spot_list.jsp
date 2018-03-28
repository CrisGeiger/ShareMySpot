<%-- 
    Document   : spot_list
    Created on : 25.03.2018, 12:00:59
    Author     : cgeiger1
--%>
<%-- 
    Document   : spot_list
    Changed on : 28.03.2018, 17:09:32
    Editor     : BeckerA
    Die Kategory als Ausgabefeld nach der Suche hinzugefügt. Da es gefehlt hat.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="ShareMySpot/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<template:base>
    <jsp:attribute name="title">
        Suchergebnisse
    </jsp:attribute>
        
    <jsp:attribute name="head">
            
    </jsp:attribute>
        <jsp:attribute name="menu">
            <div class="menuitem">
                <a href="<c:url value="/APP/spots/new"/>">Parkplatz anlegen</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="/APP/spots/own"/>">Eigene Parkplätze</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="/APP/spots/favourites"/>">Favoriten anzeigen</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="APP/user/edit"/>">Profil bearbeiten</a>
            </div>
        </jsp:attribute>
    <jsp:attribute name="content">
        <c:choose>
            <c:when test="${empty spots}">
                <p>
                    Es wurden keine Parkplätze gefunden
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="sharemyspot.web.WebUtils"/>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Postleitzahl</th>
                            <th>Ort</th>
                            <th>Straße</th>
                            <th>Hausnummer</th>
                            <th>Besitzer</th>
                            <th>Status</th>
                            <th>Kategorie</th>
                        </tr>
                    </thead>
                    <c:forEach items="${spots}" var="spot">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/spot/${spot.id}/"/>">
                                <c:out value="${spot.id}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${spot.plz}"/>
                            </td>
                            <td>
                                <c:out value="${spot.place}"/>
                            </td>
                            <td>
                                <c:out value="${spot.road}"/>
                            </td>
                            <td>
                                <c:out value="${spot.roadnumber}"/>
                            </td>
                            <td>
                                <c:out value="${spot.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${spot.status.label}"/>
                            </td>
                            <td>                      
                                <c:out value="${spot.category.label}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>