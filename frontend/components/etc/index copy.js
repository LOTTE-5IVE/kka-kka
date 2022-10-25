import { useRouter } from "next/router";
import { useQuery } from "react-query";
import axios from "axios";
import Title from "../common/Title";
import { useState } from "react";
import ProductLayout from "../../layouts/ProductLayout";

export default function productCidList() {
  const router = useRouter();
  const cat_id = router.query.cat_id;
  const search = router.query.search;

  const initpage = router.query.page;

  const [start, setStart] = useState(1);
  const [end, setEnd] = useState(10);
  const [page, setPage] = useState(1);
  const [lastPage, setLastPage] = useState();

  const { isLoading, data } = useQuery(
    ["products", cat_id, page],
    () => {
      return axios.get(`/api/products?category=${cat_id}&page=${page}`);
    },
    { enabled: !!cat_id },
  );

  if (isLoading) {
    return <h2>Loading...</h2>;
  }

  return (
    <>
      <Title title="상품목록" />
      <ProductLayout
        cat_id={cat_id}
        search={search}
        data={data}
        start={start}
        setStart={setStart}
        end={end}
        setEnd={setEnd}
        page={page}
        setPage={setPage}
        lastPage={lastPage}
        setLastPage={setLastPage}
      />
    </>
  );
}
