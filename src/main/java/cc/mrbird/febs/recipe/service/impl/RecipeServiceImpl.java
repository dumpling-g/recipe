package cc.mrbird.febs.recipe.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.recipe.entity.Recipe;
import cc.mrbird.febs.recipe.mapper.RecipeMapper;
import cc.mrbird.febs.recipe.service.IRecipeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 菜谱 Service实现
 *
 * @author MrBird
 * @date 2020-07-08 17:24:12
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public IPage<Recipe> findRecipes(QueryRequest request, Recipe recipe) {
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(recipe.getTitle())) {
            queryWrapper.like(Recipe::getTitle, recipe.getTitle());
        }
        if (recipe.getCreateTimeFrom() != null) {
            queryWrapper.ge(Recipe::getCreateTime, recipe.getCreateTimeFrom());
        }
        if (recipe.getCreateTimeTo() != null) {
            queryWrapper.le(Recipe::getCreateTime, recipe.getCreateTimeTo());
        }
        if(StringUtils.isNotBlank(recipe.getStatus())) {
            queryWrapper.eq(Recipe::getStatus, recipe.getStatus());
        }
        // TODO 设置查询条件
        Page<Recipe> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Recipe> findRecipes(Recipe recipe) {
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createRecipe(Recipe recipe) {
        this.save(recipe);
    }

    @Override
    @Transactional
    public void updateRecipe(Recipe recipe) {
        this.saveOrUpdate(recipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(String ids) {
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Recipe::getId, ids.split(","));
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    /**
     * 发布/取消发布
     *
     * @param ids ids
     * @param status id
     */
    @Override
    @Transactional
    public void changeStatus(String ids, String status){
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Recipe::getId, ids.split(","));
        Recipe recipe = new Recipe();
        recipe.setStatus(status);
        // TODO 设置删除条件
        this.update(recipe, wrapper);
    }
}
