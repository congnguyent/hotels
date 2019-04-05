package vn.gtd.data.expedia.product.repository;

import vn.gtd.data.expedia.product.entity.ProductEntity;
import vn.gtd.data.expedia.repository.WriteOnlyRepository;

public interface ProductWriteOnlyRepository extends WriteOnlyRepository<ProductEntity, String> {
}
