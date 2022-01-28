import Axios from 'axios'

import {DATA_URL, STAFF_IMG_URL} from "./api-links"
import CookiesService from "./cookies-service";

const pageLoadFunctionMap = {
    Privileges : getPrivilegesPage,
    Roles : getRolesPage,
    UsersAccounts: getUsersPage,
    StaffAccounts: getStaffPage,
    Albums : getAlbumsPage,
    Publications : getPublicationsPage,
    MatureRatings : getMatureRatingsPage,
    SearchTags: getSearchTagsPage,
    UsersHistory : getHistoryUsersPage,
    StaffHistory: getHistoryStaffPage
}

function getRequestHeaders(){
    return {
        'X-XSRF-TOKEN': CookiesService.getCSRFToken(),
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
}

function loadPage(tableId, pageNum, pageSize, sorting) {
    return pageLoadFunctionMap[tableId](pageNum, pageSize, sorting);
}

function getPrivilegesPage(pageNum, pageSize, sorting){
    return Axios.get(DATA_URL+"privileges",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderDir: sorting.direction
        }
    });
}

function getRolesPage(pageNum, pageSize, sorting){
    return Axios.get(DATA_URL+"roles",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderDir: sorting.direction
        }
    });
}

function getStaffPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"staff",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}



function getUsersPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"users",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}

function getAlbumsPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"albums",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}

function getPublicationsPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"publications",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}

function getMatureRatingsPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"mature_ratings",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderDir: sorting.direction
        }
    });
}

function getSearchTagsPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"search_tags",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderDir: sorting.direction
        }
    });
}

function getHistoryStaffPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"history/staff",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}

function getHistoryUsersPage(pageNum, pageSize, sorting) {
    return Axios.get(DATA_URL+"history/users",{
        withCredentials: true,
        params: {
            pageNumber: pageNum,
            pageSize: pageSize,
            orderField: sorting.fieldName,
            orderDir: sorting.direction
        }
    });
}

function loadThumbImage(fileName, extension) {
    return Axios.get(STAFF_IMG_URL+fileName+"-thumb."+extension.toLowerCase(),{
        withCredentials: true
    });
}

function loadViewImage(fileName, extension) {
    return Axios.get(STAFF_IMG_URL+fileName+"-view."+extension.toLowerCase(),{
        withCredentials: true
    });
}

function loadFullImage(fileName, extension) {
    return Axios.get(STAFF_IMG_URL+fileName+"-full."+extension.toLowerCase(),{
        withCredentials: true
    });
}

function getImageThumbUrl(fileName, extension){
    return STAFF_IMG_URL+fileName+"-thumb."+extension.toLowerCase();
}

export {loadPage, loadThumbImage, loadViewImage, loadFullImage, getImageThumbUrl}