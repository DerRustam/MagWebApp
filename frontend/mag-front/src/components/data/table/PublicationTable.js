import React from "react"
import {Table, Image} from "react-bootstrap";
import {BsReverseLayoutTextSidebarReverse} from "react-icons/bs"
import {strings} from "../../../locale/strings";

import {getImageThumbUrl} from "../../../services/data-service";

export default function getPublicationTable(dataList) {
    return(
        <Table striped bordered hover className="overflow-scroll">
            <thead>
                <tr>
                    <th>Roles</th>
                    <th>User Lock.</th>
                    <th>View Nickname</th>
                    <th>Name Album</th>
                    <th>Name Publication</th>
                    <th>Mature R.</th>
                    <th>Is Public</th>
                    <th>Is Locked</th>
                    <th>Date publication</th>
                    <th>Search Tags</th>
                    <th>Preview</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    if (record.extension){
                        return <tr key={record.id}>
                            <td>{record.album.userAccount.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                            <td>{record.album.userAccount.isLocked}</td>
                            <td>{record.album.userAccount.viewNickname}</td>
                            <td>{record.album.nameAlbum}</td>
                            <td>{record.namePublication}</td>
                            <td>{record.matureRating.nameRating}</td>
                            <td>{record.isPublic}</td>
                            <td>{record.isLocked}</td>
                            <td>{record.datePublication}</td>
                            <td>{record.searchTags.map(t => <div key={t.id}>{t.nameTag+", "}</div>)}</td>
                            <td><Image thumbnail src={getImageThumbUrl(record.id, record.extension)}/></td>
                            <td></td>
                        </tr>;
                    } else {
                        return <tr key={record.id}>
                            <td>{record.album.userAccount.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                            <td>{record.album.userAccount.isLocked}</td>
                            <td>{record.album.userAccount.viewNickname}</td>
                            <td>{record.album.nameAlbum}</td>
                            <td>{record.namePublication}</td>
                            <td>{record.matureRating.nameRating}</td>
                            <td>{record.isPublic}</td>
                            <td>{record.isLocked}</td>
                            <td>{record.datePublication}</td>
                            <td>{record.searchTags.map(t => <div key={t.id}>{t.nameTag+", "}</div>)}</td>
                            <td><BsReverseLayoutTextSidebarReverse size={20}/></td>
                            <td></td>
                        </tr>;
                    }
                })
            }
            </tbody>
        </Table>
    );
}