import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getStaffAccountTable(dataList) {
    return (
        <Table striped bordered hover className="overflow-scroll">
            <thead>
                <tr>
                    <th>Staff Uid</th>
                    <th>Full name</th>
                    <th>Contact Email</th>
                    <th>Contact Phone</th>
                    <th>Date Hire</th>
                    <th>Date Birth</th>
                    <th>Gender</th>
                    <th>Is Locked</th>
                    <th>Roles</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr key={record.id}>
                        <td>{record.staffMemberUid}</td>
                        <td>{record.fullName}</td>
                        <td>{record.contactEmail}</td>
                        <td>{record.contactPhone}</td>
                        <td>{record.dateHire}</td>
                        <td>{record.dateBirth}</td>
                        <td>{record.gender}</td>
                        <td>{record.isLocked}</td>
                        <td>{record.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}