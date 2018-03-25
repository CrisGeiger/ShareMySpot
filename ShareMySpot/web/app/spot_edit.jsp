<%-- 
    Document   : spot_edit
    Created on : 23.03.2018, 10:16:07
    Author     : JU_FI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="ShareMySpot/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

  

<template:base> 
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Spot bearbeiten
            </c:when>
            <c:otherwise>
                Spot anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/spot_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/spots/"/>">Übersicht</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                
                
                <label for="spot_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="spot_category" <c:if test="${owner.username != user.username}">disabled="true"</c:if>>
                        <option value="Car">Auto</option>
                        <option value="Motorcycle">Motorrad</option>
                    </select>
                </div>
                        
                <label for="spot_status">Status:</label>
                <div class="side-by-side">
                    <select name="spot_status" <c:if test="${owner.username != user.username}">disabled="true"</c:if>>
                        <option value="FREE">frei</option>
                        <option value="NOT_AVAILABLE">nicht verfügbar</option>
                    </select>
                </div>
                        
                <label for="spot_place">
                        Ort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_place" value="${task_form.values["spot_place"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>
                    
                    <label for="spot_plz">
                        Postleitzahl:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_plz" value="${task_form.values["spot_plz"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>
                    
                    <label for="spot_road">
                        Straße:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_road" value="${task_form.values["spot_road"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>
                    
                    <label for="spot_roadNumber">
                        Nummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_roadNumber" value="${task_form.values["spot_roadNumber"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>

                <label for="spot_description">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="spot_description" <c:if test="${owner.username != user.username}">readonly="true"</c:if>><c:out value="${anzeige_form.values['spot_description'][0]}"/></textarea>
                </div>
                
                   
                 <label for="spot_FreeFrom">
                        Frei vom:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_FreeFrom" value="${task_form.values["spot_FreeFrom"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>
                    
                    <label for="spot_FreeTo">
                        Frei bis:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="spot_FreeTo" value="${task_form.values["spot_FreeTo"][0]}"<c:if test="${owner.username != user.username}">readonly="true"</c:if>>
                    </div>
               

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save" <c:if test="${owner.username != user.username}">disabled="true"</c:if>>
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete"<c:if test="${owner.username != user.username}">disabled="true"</c:if>>
                            Löschen
                        </button>
                    </c:if>
                </div>
                <div>
                <%-- Datum und Anbieter --%>
                <h4>Angelegt am:</h4>
                ${datum}
                
                <h4>Anbieter:</h4>
                ${owner.name} <br>
                ${owner.anschrift} <br>
                ${owner.plz}
                ${owner.ort}
                </div>
          
            <%-- Fehlermeldungen --%>
            <c:if test="${!empty spot_form.errors}">
                <ul class="errors">
                    <c:forEach items="${spot_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>