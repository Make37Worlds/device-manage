package com.dlut.efmsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlut.efmsystem.pojo.AddDeclaration;

import java.util.List;

public interface AddDeclarationService {
    IPage<AddDeclaration> getPage(int currentPage, int pageSize, AddDeclaration addDeclaration);
    IPage<AddDeclaration> getUnapproved(int currentPage, int pageSize, String id);
    AddDeclaration getOneAddDeclaration(int id);
    int create(AddDeclaration addDeclaration);
    Boolean setApprovalInfo(AddDeclaration addDeclaration);
}
