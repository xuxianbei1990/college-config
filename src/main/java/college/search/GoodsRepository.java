package college.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Name
 *
 * @author admin
 * Date 2019/2/19
 * VersionV1.0
 * @description
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo, Long> {
}
