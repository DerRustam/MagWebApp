import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getUserHistoryTable(dataList) {
    return(
        <Table striped bordered hover className="overflow-scroll">
            <thead>
                <tr>
                    <th>Roles</th>
                    <th>User Lock.</th>
                    <th>ViewNickname</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Birth</th>
                    <th>Name Publication</th>
                    <th>Mature Rating</th>
                    <th>Date Publication</th>
                    <th>Datetime Issue</th>
                    <th>Description History</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr id={record.id}>
                        <td>{record.account.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                        <td>{record.account.isLocked}</td>
                        <td>{record.account.viewNickName}</td>
                        <td>{record.account.firstName}</td>
                        <td>{record.account.lastName}</td>
                        <td>{record.account.dateBirth}</td>
                        <td>{record.publication.namePublication}</td>
                        <td>{record.publication.matureRating.nameRating}</td>
                        <td>{record.publication.datePublication}</td>
                        <td>{record.datetimeIssue}</td>
                        <td>{record.descriptionHistory}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}