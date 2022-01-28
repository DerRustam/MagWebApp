
export const TableOperation = {
    None: 0,
    Add: 1,
    Update: 2,
    Delete: 3,
    DeleteSequence: 4
}

export const FieldType = {
    Text: 0,
    Password: 1,
    Email: 2,
    Date: 3,
    DateTime: 4
}

export const DefaultTableName = "Accounts";
export const TableNames = [
    "Privileges",
    "Roles",
    "Accounts",
    "Albums",
    "Publications",
    "MatureRatings",
    "SearchTags",
    "History"
]

export const SortableTables = ["Accounts", "Albums", "Publications", "History"]

export const ClassificationTables = ["Accounts", "History"]




