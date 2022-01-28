import getUserAccountTable from "./UserAccountTable";
import getPrivilegesTable from "./PrivilegesTable";
import getRolesTable from "./RolesTable";
import getStaffAccountTable from "./StaffAccountTable";
import getAlbumTable from "./AlbumTable";
import getPublicationTable from "./PublicationTable";
import getSearchTagTable from "./SearchTagTable";
import getMatureRatingTable from "./MatureRatingTable";
import getUserHistoryTable from "./UserHistoryTable";
import getStaffHistoryTable from "./StaffHistoryTable";


export default function getTableWithNameAndData(tableName, dataList) {
    switch (tableName) {
        case "Privileges":{
            return getPrivilegesTable(dataList);
        }
        case "Roles": {
            return getRolesTable(dataList);
        }
        case "UsersAccounts" : {
            return getUserAccountTable(dataList) ;
        }
        case "StaffAccounts":{
            return getStaffAccountTable(dataList);
        }
        case "Albums":{
            return getAlbumTable(dataList);
        }
        case "Publications":{
            return getPublicationTable(dataList);
        }
        case "SearchTags":{
            return getSearchTagTable(dataList);
        }
        case "MatureRatings":{
            return getMatureRatingTable(dataList);
        }
        case "UsersHistory":{
            return getUserHistoryTable(dataList);
        }
        case "StaffHistory":{
            return getStaffHistoryTable(dataList);
        }
        default:{
            return null;
        }
    }
}