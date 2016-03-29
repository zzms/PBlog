package com.pblog.service.article;

import com.pblog.dao.ArticleInfoMapper;
import com.pblog.dao.SlugInfoMapper;
import com.pblog.domain.ArticleInfo;
import com.pblog.domain.SlugInfo;
import com.pblog.web.controller.articlemanage.ArticleInfoFormBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("manageArticleInfoService")
@Transactional
public class ManageArticleInfoServiceImpl implements ManageArticleInfoService {

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private SlugInfoMapper slugInfoMapper;

    public void insert(ArticleInfoFormBean articleInfoFormBean) throws RuntimeException{
        ArticleInfo articleInfo = transFormBeanToArticle(articleInfoFormBean);
        articleInfoMapper.insert(articleInfo);

        SlugInfo slugInfo = slugInfoMapper.queryById(articleInfo.getId());
        articleInfo.setSlug(slugInfo.getSlug());
        articleInfoMapper.update(articleInfo);
    }

    public List<ArticleInfo> findListByCategorySlug(Long categorySlug) {
        List<ArticleInfo> articleInfoList;
        if (null == categorySlug){
            articleInfoList = articleInfoMapper.findList();
            //articleInfoList = articleInfoMapper.findList();
        }else{
            articleInfoList = articleInfoMapper.findListByCategorySlug(categorySlug);
        }

        return articleInfoList;
    }

    public int updateArticleEnabled(int articleId, boolean enabled) {
        articleInfoMapper.updateArticleEnabled(articleId, enabled);
        return 0;
    }

    private ArticleInfo transFormBeanToArticle(ArticleInfoFormBean articleInfoFormBean){
        ArticleInfo articleInfo = new ArticleInfo();

        articleInfo.setCategorySlug(articleInfoFormBean.getCategorySlug());
        articleInfo.setTag(articleInfoFormBean.getTag());
        articleInfo.setTitle(articleInfoFormBean.getTitle());
        articleInfo.setContent(articleInfoFormBean.getContent());
        articleInfo.setDescription(articleInfoFormBean.getDescription());
        articleInfo.setEnabled(true);
        articleInfo.setLastModifyTime(new Date());
        articleInfo.setCreateTime(new Date());
        articleInfo.setThumb(0);

        return articleInfo;
    }
}