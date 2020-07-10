package cc.mrbird.febs.recipe.service;

import cc.mrbird.febs.recipe.entity.Recipe;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜谱 Service接口
 *
 * @author MrBird
 * @date 2020-07-08 17:24:12
 */
public interface IRecipeService extends IService<Recipe> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param recipe recipe
     * @return IPage<Recipe>
     */
    IPage<Recipe> findRecipes(QueryRequest request, Recipe recipe);

    /**
     * 查询（所有）
     *
     * @param recipe recipe
     * @return List<Recipe>
     */
    List<Recipe> findRecipes(Recipe recipe);

    /**
     * 新增
     *
     * @param recipe recipe
     */
    void createRecipe(Recipe recipe);

    /**
     * 修改
     *
     * @param recipe recipe
     */
    void updateRecipe(Recipe recipe);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRecipe(String ids);

    /**
     * 发布/取消发布
     *
     * @param ids ids
     * @param status status
     */
    void changeStatus(String ids, String status);
}
