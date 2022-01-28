import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getStaffHistoryTable(dataList) {
    return(
        <Table striped bordered hover className="overflow-scroll">
            <thead>
                <tr>
                    <th>Roles</th>
                    <th>Is Locked</th>
                    <th>Staff Uid</th>
                    <th>Contact Email</th>
                    <th>Full Name</th>
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
                        <td>{record.account.staffMemberUid}</td>
                        <td>{record.contactEmail}</td>
                        <td>{record.fullName}</td>
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