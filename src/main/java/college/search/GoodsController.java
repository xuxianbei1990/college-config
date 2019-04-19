package college.search;

import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Name
 *
 * @author admin
 * Date 2019/2/19
 * VersionV1.0
 * @description
 *
 *
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("save")
    public String save() {
        GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),
                "商品" + System.currentTimeMillis(), "测试商品");
        return goodsRepository.save(goodsInfo).toString();
    }

    @GetMapping("delete")
    public String delete(long id) {
        goodsRepository.deleteById(id);
        return "congratulations";
    }

    @GetMapping("update")
    public String update(long id, String name, String description) {
        GoodsInfo goodsInfo = new GoodsInfo(id, name, description);
        goodsRepository.save(goodsInfo);
        return "congratulations";
    }

    @GetMapping("getOne")
    public GoodsInfo getOne(long id) {
        Optional<GoodsInfo> optional = goodsRepository.findById(id);
        return optional.get();
    }

    private Integer PAGESIZE = 10;

    @GetMapping("getGoodsList")
    public List<GoodsInfo> getList(Integer pageNumber, String query) {
        if (pageNumber == null) pageNumber = 0;
        SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        Page<GoodsInfo> goodsInfoPage = goodsRepository.search(searchQuery);
        return goodsInfoPage.getContent();
    }

    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        MatchPhraseQueryBuilder builder = QueryBuilders.matchPhraseQuery("name", searchContent);
        WeightBuilder weightBuilder = ScoreFunctionBuilders.weightFactorFunction(100);
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];
        filterFunctionBuilders[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(builder, weightBuilder);
        filterFunctionBuilders[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchPhraseQuery("description", searchContent), weightBuilder);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilders);
        // 设置权重分 求和模式
        functionScoreQueryBuilder.scoreMode(FunctionScoreQuery.ScoreMode.SUM);
        // 设置权重分 最低分
        functionScoreQueryBuilder.setMinScore(10);

        //设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

}
