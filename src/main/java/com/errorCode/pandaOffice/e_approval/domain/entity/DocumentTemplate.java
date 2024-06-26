package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.CreateApprovalDocumentTemplateRequest;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.UpdateApprovalDocumentTemplateRequest;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "approval_document_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
/* 결재 문서 양식 */
public class DocumentTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 문서 양식 이름 */
    @Column(nullable = false)
    private String title;
    /* 문서 양식 */
    @Column(nullable = false)
    private String document;
    /* 사용 상태 */
    @Column(nullable = false)
    private boolean status;
    /* 최종 수정자 */
    @ManyToOne
    @JoinColumn(name = "last_editor_id", nullable = false)
    private Employee lastEditor;
    /* 자동 결재선 ID */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_id")
    private List<AutoApprovalLine> autoApprovalLines;
    /* 양식 상위 폴더 */
    @Column(nullable = false)
    private int folderId;

    public static DocumentTemplate of(CreateApprovalDocumentTemplateRequest request, List<AutoApprovalLine> lineEntityList, Employee lastEditorEntity) {
        DocumentTemplate templateEntity = new DocumentTemplate();

        templateEntity.title = request.getTitle();
        templateEntity.document = request.getDocument();
        templateEntity.status = true;
        templateEntity.lastEditor = lastEditorEntity;
        templateEntity.autoApprovalLines = lineEntityList;
        templateEntity.folderId = request.getFolderId();

        return templateEntity;
    }


    public void modifyByRequest(UpdateApprovalDocumentTemplateRequest request, List<AutoApprovalLine> lineEntity) {
        this.title = request.getTitle();
        this.document = request.getDocument();
        this.status = request.isStatus();
        this.folderId = request.getFolderId();
        this.autoApprovalLines = lineEntity;
    }
}
