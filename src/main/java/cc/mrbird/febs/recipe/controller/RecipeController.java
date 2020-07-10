package cc.mrbird.febs.recipe.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.recipe.entity.Recipe;
import cc.mrbird.febs.recipe.service.IRecipeService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜谱 Controller
 *
 * @author MrBird
 * @date 2020-07-08 17:24:12
 */
@Slf4j
@Validated
@Controller
public class RecipeController extends BaseController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "recipe")
    public String recipeIndex() {
        return FebsUtil.view("recipe/recipe");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "recipe/add")
    public String tariffAdd(Model model) {
        return FebsUtil.view("recipe/add");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "recipe/edit/{id}")
    public String recipeUpdate(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.getById(id);
        model.addAttribute("recipe", recipe);
        return FebsUtil.view("recipe/edit");
    }

    @GetMapping("recipe")
    @ResponseBody
    @RequiresPermissions("recipe:list")
    public FebsResponse getAllRecipes(Recipe recipe) {
        return new FebsResponse().success().data(recipeService.findRecipes(recipe));
    }

    @GetMapping("recipe/list")
    @ResponseBody
    @RequiresPermissions("recipe:list")
    public FebsResponse recipeList(QueryRequest request, Recipe recipe) {
        Map<String, Object> dataTable = getDataTable(this.recipeService.findRecipes(request, recipe));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Recipe", exceptionMessage = "新增Recipe失败")
    @PostMapping("recipe")
    @ResponseBody
    @RequiresPermissions("recipe:add")
    public FebsResponse addRecipe(@Valid Recipe recipe) {
        this.recipeService.createRecipe(recipe);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Recipe", exceptionMessage = "删除Recipe失败")
    @GetMapping("recipe/delete/{ids}")
    @ResponseBody
    @RequiresPermissions("recipe:delete")
    public FebsResponse deleteRecipe(@PathVariable String ids) {
        this.recipeService.deleteRecipe(ids);
        return new FebsResponse().success();
    }

    @GetMapping("recipe/changeStatus/{status}/{ids}")
    @ResponseBody
    @RequiresPermissions("recipe:publish")
    public FebsResponse changeStatus(@PathVariable String ids,@PathVariable String status) {
        this.recipeService.changeStatus(ids, status);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Recipe", exceptionMessage = "修改Recipe失败")
    @PostMapping("recipe/update")
    @ResponseBody
    @RequiresPermissions("recipe:update")
    public FebsResponse updateRecipe(Recipe recipe) {
        this.recipeService.updateRecipe(recipe);
        return new FebsResponse().success();
    }

}
