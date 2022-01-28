package com.rustam.magbackend.controller;

import com.rustam.magbackend.dto.data.*;
import com.rustam.magbackend.model.History;
import com.rustam.magbackend.model.Publication;
import com.rustam.magbackend.service.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataRequestController {


    private final AccountService accountService;
    private final AlbumService albumService;
    private final PublicationService publicationService;
    private final HistoryService historyService;
    private final RoleService roleService;

    @Autowired
    public DataRequestController(AccountService accountService, AlbumService albumService, PublicationService publicationService, HistoryService historyService, RoleService roleService) {
        this.accountService = accountService;
        this.albumService = albumService;
        this.publicationService = publicationService;
        this.historyService = historyService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/privileges", produces = "application/json")
    public ResponseEntity<Page<PrivilegeDTO>> getPrivilegePage(@RequestParam(defaultValue = "0") int pageNumber,
                                                               @RequestParam(defaultValue = "20") int pageSize,
                                                               @RequestParam(defaultValue = "ASC") String orderDir){
        return ResponseEntity.ok(roleService.getPageViewPrivilege(pageNumber, pageSize, orderDir));
    }

    @GetMapping(value = "/privileges/src", produces = "application/json")
    public ResponseEntity<List<PrivilegeDTO>> getListPrivilegeLike(
            @RequestParam(defaultValue = "") String namePrivilege,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(roleService.getKeyListPrivilege(namePrivilege, limit));
    }

    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<Page<RoleDTO>> getRolePage(@RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "20") int pageSize,
                                                     @RequestParam(defaultValue = "ASC") String orderDir){
        return ResponseEntity.ok(roleService.getPageViewRole(pageNumber, pageSize, orderDir));
    }

    @GetMapping(value = "/roles/src", produces = "application/json")
    public ResponseEntity<List<RoleDTO>> getListRoleLike(
            @RequestParam(defaultValue = "") String nameRole,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(roleService.getKeyListRole(nameRole, limit));
    }

    @GetMapping(value = "/staff", produces = "application/json")
    public ResponseEntity<Page<StaffAccountDTO>> getPageStaff(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "fullName") String orderField,
            @RequestParam(defaultValue = "ASC") String orderDir
    ){
        return ResponseEntity.ok(accountService.getPageViewStaffAccount(pageNumber, pageSize,  orderDir, orderField));
    }

    @GetMapping(value = "/staff/src", produces = "application/json")
    public ResponseEntity<List<StaffAccountDTO>> getListStaffLike(
            @RequestParam(defaultValue = "") String staffMemberUid,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(accountService.getKeyListStaffAccount(staffMemberUid, limit));
    }

    @PostMapping(value = "/staff", produces = "application/json")
    public ResponseEntity<StaffAccountDTO> addStaffAccount(@RequestBody @Valid StaffAccountDTO staffAccountDTO){
        return ResponseEntity.ok(accountService.addStaffAccount(staffAccountDTO));
    }

    @PutMapping(value = "/staff/{id}", produces = "application/json")
    public ResponseEntity<StaffAccountDTO> updateStaffAccount(@PathVariable(required = false) Long id, @RequestBody StaffAccountDTO staffAccountDTO){
        if (id != null && id != 0){
            staffAccountDTO.setId(id);
        }
        return ResponseEntity.ok(accountService.updateStaffAccount(staffAccountDTO));
    }

    @DeleteMapping("/staff/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStaffAccount(@PathVariable Long id){
        accountService.deleteStaffAccount(id);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Page<UserAccountDTO>> getPageUser(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "viewNickname") String orderField,
            @RequestParam(defaultValue = "ASC") String orderDir
    ){
        return ResponseEntity.ok(accountService.getPageViewUserAccount(pageNumber, pageSize,  orderDir, orderField));
    }

    @GetMapping(value = "/users/src", produces = "application/json")
    public ResponseEntity<List<UserAccountDTO>> getListUserLike(
            @RequestParam(defaultValue = "") String viewNickname,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(accountService.getKeyListUserAccount(viewNickname, limit));
    }

    @PostMapping(value = "/users", produces = "application/json")
    public ResponseEntity<UserAccountDTO> addUserAccount(@RequestBody @Valid UserAccountDTO user){
        return ResponseEntity.ok(accountService.addUserAccount(user));
    }

    @PutMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<UserAccountDTO> updateUserAccount(@PathVariable(required = false) Long id, @RequestBody UserAccountDTO user){
        if (id != null && id != 0){
            user.setId(id);
        }
        return ResponseEntity.ok(accountService.updateUserAccount(user));
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserAccount(@PathVariable Long id){
        accountService.deleteUserAccount(id);
    }

    @GetMapping(value = "/albums", produces = "application/json")
    public ResponseEntity<Page<AlbumDTO>> getPageAlbum(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "nameAlbum") String orderField,
            @RequestParam(defaultValue = "ASC") String orderDir
    ){
        return ResponseEntity.ok(albumService.getPageViewAlbum(pageNumber, pageSize,  orderDir, orderField));
    }

    @GetMapping(value = "/albums/src", produces = "application/json")
    public ResponseEntity<List<AlbumDTO>> getListAlbumLike(
            @RequestParam Long idAccount,
            @RequestParam(defaultValue = "") String nameAlbum,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(albumService.getKeyListViewAlbum(idAccount, nameAlbum, limit));
    }

    @GetMapping(value = "/publications", produces = "application/json")
    public ResponseEntity<Page<PublicationDTO>> getPagePublication(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "namePublication") String orderField,
            @RequestParam(defaultValue = "ASC") String orderDir
    ){
        return ResponseEntity.ok(publicationService.getPageViewPublication(pageNumber, pageSize, orderField, orderDir));
    }

    @GetMapping(value = "/publications/src", produces = "application/json")
    public ResponseEntity<List<PublicationDTO>> getListPublicationLike(
            @RequestParam Long idAlbum,
            @RequestParam(defaultValue = "") String namePublication,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(publicationService.getKeyListPublication(idAlbum, namePublication, limit));
    }

    @PostMapping(value = "/publications") //consumes = "multipart/form-data", produces = "application/json") //!возможно не нужно
    public ResponseEntity<PublicationDTO> addPublication(
            @RequestParam PublicationDTO publication,
            @RequestParam(required = false) MultipartFile imageFile){
        return ResponseEntity.ok(publicationService.addPublication(publication, imageFile));
    }

    @PutMapping(value = "/publications/{id}")//, consumes = "multipart/form-data", produces = "application/json") //!возможно не нужно
    public ResponseEntity<PublicationDTO> addPublication(
            @PathVariable(required = false) Long id,
            @RequestParam PublicationDTO publication,
            @RequestParam(required = false) MultipartFile imageFile){
        if (id != null && id != 0){
            publication.setId(id);
        }
        return ResponseEntity.ok(publicationService.updatePublication(publication, imageFile));
    }

    @DeleteMapping(value = "/publications/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePublication(@PathVariable Long id){
        publicationService.deletePublication(id);
    }

    @GetMapping("/mature_ratings")
    public ResponseEntity<Page<MatureRatingDTO>> getPageMatureRating(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "DESC") String orderDir
    ){
        return ResponseEntity.ok(publicationService.getPageViewMatureRating(pageNumber, pageSize, orderDir));
    }

    @GetMapping("/mature_ratings/src")
    public ResponseEntity<List<MatureRatingDTO>> getListMatureRatingLike(
            @RequestParam(defaultValue = "") String nameRating,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(publicationService.getKeyListMatureRating(nameRating, limit));
    }

    @GetMapping("/search_tags")
    public ResponseEntity<Page<SearchTagDTO>> getPageSearchTag(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "DESC") String orderDir
    ){
        return ResponseEntity.ok(publicationService.getPageViewSearchTag(pageNumber, pageSize, orderDir));
    }

    @GetMapping("/search_tags/src")
    public ResponseEntity<List<SearchTagDTO>> getListSearchTagLike(
            @RequestParam(defaultValue = "") String nameTag,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(publicationService.getKeyListSearchTag(nameTag, limit));
    }

    @GetMapping(value = "/history/staff", produces = "application/json")
    public ResponseEntity<Page<HistoryDTO>> getStaffHistoryPage(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "datetimeIssue") String orderField,
            @RequestParam(defaultValue = "DESC") String orderDir
    ){
        return ResponseEntity.ok(historyService.getPageViewStaffHistory(pageNumber, pageSize, orderField, orderDir));
    }

    @GetMapping(value = "/history/users", produces = "application/json")
    public ResponseEntity<Page<HistoryDTO>> getHistoryPage(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "datetimeIssue") String orderField,
            @RequestParam(defaultValue = "DESC") String orderDir
    ){
        return ResponseEntity.ok(historyService.getPageViewUserHistory(pageNumber, pageSize, orderField, orderDir));
    }

    @GetMapping(value = "/history/src", produces = "application/json")
    public ResponseEntity<List<HistoryDTO>> getListHistoryLike(
            @RequestParam Long idAccount,
            @RequestParam Long idPublication,
            @RequestParam(defaultValue = "2015-01-01") String dateFrom,
            @RequestParam(defaultValue = "15") Integer limit
    ){
        return ResponseEntity.ok(historyService.getKeyListHistory(idAccount, idPublication, dateFrom, limit));
    }

}
