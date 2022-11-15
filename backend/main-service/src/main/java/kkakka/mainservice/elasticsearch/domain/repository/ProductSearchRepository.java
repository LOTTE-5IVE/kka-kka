package kkakka.mainservice.elasticsearch.domain.repository;


import kkakka.mainservice.elasticsearch.domain.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

}
