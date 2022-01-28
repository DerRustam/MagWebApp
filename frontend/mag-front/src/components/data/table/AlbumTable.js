import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getAlbumTable(dataList) {
    return(
        <Table striped bordered hover className="overflow-scroll">
            <thead>
                <tr>
                    <th>Roles</th>
                    <th>Is Locked</th>
                    <th>View Nickname</th>
                    <th>Name Album</th>
                    <th>Date Creation</th>
                    <th>Description Album</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr key={record.id}>
                        <td>{record.userAccount.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                        <td>{record.userAccount.isLocked}</td>
                        <td>{record.userAccount.viewNickname}</td>
                        <td>{record.nameAlbum}</td>
                        <td>{record.dateCreation}</td>
                        <td>{record.descriptionAlbum}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}