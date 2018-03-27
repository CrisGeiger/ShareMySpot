<%-- 
    Document   : booking_list.jsp
    Created on : 26.03.2018, 12:55:51
    Author     : Alexander Becker

Diese JSP zeigt die aktuellen gebuchten Parkplätze des angemeldeten Benutzers an.

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="ShareMySpot/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>



<template:base>
    <jsp:attribute name="title">
        Buchungen
    </jsp:attribute>
        
    <jsp:attribute name="head">
            <%-- 
            Im folgenden attribute name="menu" stehen die Menüfelder, die beim Anklicken den Benutzer zu einer anderen Aktionsseite hinführen. 
            "Favoriten anzeigen" ist einer der Menüfelder
            --%>
    </jsp:attribute>
        <jsp:attribute name="menu">
            <div class="menuitem">
                <a href="<c:url value="/Spot_edit"/>">Parkplatz anlegen</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="/Spots"/>">Parkplatz suchen</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="/Favorites"/>">Favoriten anzeigen</a>
            </div>
            <div class="menuitem">
                <a href="<c:url value="/app/user/"/>">Profil bearbeiten</a>
            </div>
        </jsp:attribute>
        
         <%-- 
             Der Abschnitt test="${empty spots}" mit einem kleinen Text<p>"text"</p> erscheint, 
            wenn keine Buchungen des Benutzers getätigt wurden.  
            
            --%>
    <jsp:attribute name="content">
        <c:choose>
            <c:when test="${empty spots}">
                <p>
                    Es wurden keine Buchungen gefunden.
                </p>
            </c:when>
                <%-- 
             Der Abschnitt ab "otherwise" erscheint, 
            wenn Buchungen des Benutzers existieren. Dann werden in einer Tabelle die 
            angegebenen Spaltenkriterien 
                            <th>Buchungsnummer</th>
                            <th>Startdatum</th>
                            <th>Enddatum</th>
                            <th>Status</th>
                            <th>Plz</th>
                            <th>Ort</th>
                            <th>Straße</th>
                            <th>Hausnummer</th>
                            <th>Benutzer</th>
                            <th>Kategoriedem</th>
            dem Benutzer von seinen Buchungen angezeigt.
                Dieser Inhalt steht ab Abschnitt " <c:forEach items="${booking}" var="booking"> ".
            --%>
            <c:otherwise>
                <jsp:useBean id="utils" class="sharemyspot.web.WebUtils"/>
                <table>
                    <thead>
                        <tr>
                            <th>Buchungsnummer</th>
                            <th>Startdatum</th>
                            <th>Enddatum</th>
                            <th>Status</th>
                            <th>Plz</th>
                            <th>Ort</th>
                            <th>Straße</th>
                            <th>Hausnummer</th>
                            <th>Benutzer</th>
                            <th>Kategorie</th>
                        </tr>
                    </thead>
                    
                    <c:forEach items="${booking}" var="booking">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/booking/${booking.id}/"/>">
                                <c:out value="${booking.id}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${booking.startDate}"/>
                            </td>
                            <td>
                                <c:out value="${booking.endDate}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.plz}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.place}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.road}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.roadNumber}"/>
                            </td>
                            <td>
                                <c:out value="${booking.spot.owner.username}"/>
                            </td>
                               <td>
                                <c:out value="${booking.spot.category.label}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>

