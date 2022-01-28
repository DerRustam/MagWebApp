CREATE DATABASE	blog_hosting
	WITH
	OWNER = ee_app
	ENCODING = 'UTF8'
	CONNECTION LIMIT = -1
	
CREATE TABLE	privilege
(
	id_privilege			smallserial	PRIMARY KEY,
	name_privilege			varchar(20)	NOT NULL UNIQUE,
	description_privilege	varchar(200)	NOT NULL
);

CREATE TABLE	role
(
	id_role				smallserial	PRIMARY KEY,
	name_role			varchar(20) NOT NULL UNIQUE,
	description_role	varchar(200) NOT NULL
);

CREATE TABLE	rolePrivilege
(
	id_role			smallint	REFERENCES	role(id_role) ON DELETE CASCADE,
	id_privilege	smallint	REFERENCES privilege(id_privilege) ON DELETE CASCADE,
	PRIMARY KEY(id_privilege, id_role)
);

CREATE TABLE	account
(
	id_account		bigserial	PRIMARY KEY,
	username		varchar(32)	NOT NULL UNIQUE,
	email			varchar(254)	NOT NULL UNIQUE,
	password_hash	varchar(60)	NOT NULL,
	is_locked		boolean	NOT NULL,
	discrim_account	smallint	NOT NULL
);

CREATE TABLE accountRole
(
	id_account		bigint	REFERENCES	account(id_account) ON DELETE CASCADE,
	id_role			smallint	REFERENCES	role(id_role) ON DELETE CASCADE,
	PRIMARY KEY(id_account, id_role)
);

CREATE TABLE	staffAccount
(
	id_account		bigint	PRIMARY KEY REFERENCES	account(id_account),
	id_staff_member varchar(50) NOT NULL UNIQUE,
	contact_email	varchar(254)	NOT NULL,
	contact_phone	varchar(20),
	full_name		varchar(100)	NOT NULL,
	date_hire		date	NOT NULL,
	date_birth		date	NOT NULL,
	gender			varchar(1) NOT NULL
);

CREATE TABLE	userAccount
(
	id_account		bigint	PRIMARY KEY REFERENCES	account(id_account),
	view_nickname	varchar(32)	NOT NULL UNIQUE,
	first_name		varchar(28)	NOT NULL,
	last_name		varchar(40),
	date_birth		date,
	gender			varchar(1),
	about			varchar(300)
);

CREATE TABLE	album
(
	id_album	bigserial	PRIMARY KEY,
	id_user		bigint NOT NULL	REFERENCES userAccount(id_account) ON DELETE CASCADE,
	name_album	varchar(40) NOT NULL,
	UNIQUE(id_user, name_album),
	date_creation	date NOT NULL,
	description_album	varchar(300)
);

CREATE TABLE	matureRating
(
	id_mature_rating	smallserial	PRIMARY KEY,
	name_rating	varchar(5)	NOT NULL UNIQUE,
	description_rating	varchar(500) NOT NULL
);

CREATE TABLE	publication
(
	id_publication	bigserial	PRIMARY KEY,
	id_album	bigint	REFERENCES	album(id_album) NOT NULL,
	name_publication	varchar(40)	NOT NULL,
	UNIQUE(id_album, name_publication),
	id_mature_rating	smallint	REFERENCES	matureRating(id_mature_rating),
	is_public	boolean	NOT NULL,
	is_locked	boolean NOT NULL,
	date_publication	date	NOT NULL,
	discrim_publication	smallint	NOT NULL
);

CREATE TABLE	history
(
	id_history	bigserial	PRIMARY KEY,
	id_account		bigint	NOT NULL REFERENCES account(id_account) ,
	id_publication	bigint		REFERENCES	publication(id_publication),
	datetime_issue	timestamp	NOT NULL,
	UNIQUE(id_account, id_publication, datetime_issue),
	description_history	varchar(300) NOT NULL
);

CREATE TABLE	searchTag
(
	id_tag	bigserial	PRIMARY KEY,
	name_tag	varchar(16)	NOT NULL UNIQUE
);

CREATE TABLE	publicationTag
(
	id_tag			bigint	REFERENCES	searchTag(id_tag)	ON DELETE CASCADE,
	id_publication	bigint	REFERENCES	publication(id_publication)	ON DELETE CASCADE,
	PRIMARY KEY(id_tag, id_publication)
);

CREATE TABLE	note
(
	id_publication bigint	PRIMARY KEY REFERENCES	publication(id_publication) ON DELETE CASCADE,
	text_note	varchar(8192)	NOT NULL
);

CREATE TABLE	picture
(
	id_publication bigint	PRIMARY KEY REFERENCES	publication(id_publication) ON DELETE CASCADE,
	extension	VARCHAR(5)	NOT NULL,
	picture_description	varchar(300)
);

CREATE TABLE	pictureImage
(
	id_publication bigint	REFERENCES	picture(id_publication) ON DELETE CASCADE,
	type_size	smallint	NOT NULL,	
	PRIMARY KEY(id_publication, type_size),
	image bytea NOT NULL
);

CREATE FUNCTION getPrivilegeId(name_privilege_param varchar(20))
RETURNS smallint AS $$
DECLARE res smallint;
	BEGIN
		SELECT id INTO res FROM privilege WHERE name_privilege = name_privilege_param;
		RETURN res;
	END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION getRoleId(name_role_param varchar(20))
RETURNS smallint AS $$
DECLARE res smallint;
	BEGIN
		SELECT id INTO res FROM role WHERE name_role = name_role_param;
		RETURN res;
	END;
$$ LANGUAGE plpgsql;

INSERT INTO privilege (name_privilege, description_privilege) VALUES
('PM_PUBLICATION_RD','Can read publications data'),
('PM_PUBLICATION_WR','Can write/update publications data'),
('PM_USER_RD','Can read users data'),
('PM_USER_WR','Can write/update users data'),
('PM_STAFF_RD','Can read staff data'),
('PM_STAFF_WR','Can write/update staff data'),
('PM_STRUCT_WR','Can write/update role/privilege data');

INSERT INTO role (name_role, description_role) VALUES
('R_OBSERVER','Staff member with checking publications privilege'),
('R_MODERATOR','Staff member with moderating publications privilege'),
('R_HEAD_MODERATOR','Staff member with checking accounts and moderating publications privilege'),
('R_ADMIN','Administrator with updating users/publications data privilege'),
('R_HEAD_ADMIN','Head role. Can update account(users and staff)/publication data as well as Role/Privilege restrictions');

INSERT INTO rolePermission (id_role, id_permission) VALUES
(getRoleId('R_OBSERVER'), getPermissionId('PM_PUBLICATION_RD')),

(getRoleId('R_MODERATOR'), getPermissionId('PM_PUBLICATION_WR')),

(getRoleId('R_HEAD_MODERATOR'), getPermissionId('PM_USER_RD')),

(getRoleId('R_ADMIN'), getPermissionId('PM_USER_WR')),

(getRoleId('R_HEAD_ADMIN'), getPermissionId('PM_STAFF_RD')),
(getRoleId('R_HEAD_ADMIN'), getPermissionId('PM_STAFF_WR')),
(getRoleId('R_HEAD_ADMIN'), getPermissionId('PM_STRUCT_WR'));


DROP FUNCTION getcGenreId;
DROP FUNCTION getsAlbumId;
DROP FUNCTION getrAccountId;
DROP FUNCTION getsPublicationId;