import { useRouter } from "next/router";
import Title from "../../components/common/Title";
import { useState } from "react";
import ProductLayout from "../../layouts/ProductLayout";

export default function productCidList() {
  const router = useRouter();
  const cat_id = router.query.cat_id;
  const search = router.query.search;

  const [page, setPage] = useState(1);
  const [lastPage, setLastPage] = useState();

  return (
    <>
      <Title title="상품목록" />
      <ProductLayout
        cat_id={cat_id}
        search={search}
        page={page}
        setPage={setPage}
        lastPage={lastPage}
        setLastPage={setLastPage}
      />
    </>
  );
}
