import { useRouter } from "next/router";
import { useQuery } from "react-query";
import axios from "axios";
import Title from "../../components/common/Title";
import { useState } from "react";
import ProductLayout from "../../layouts/ProductLayout";

export default function productCidList() {
  const [state, setState] = useState([]);
  // 카테고리 아이디별 호출
  // https://nomadcoders.co/nextjs-fundamentals/lectures/3451 7분 참고
  const router = useRouter();
  const cat_id = router.query.cat_id;
  const search = router.query.search;

  const { isLoading, data } = useQuery(
    ["products", cat_id],
    () => {
      return axios.get(`/api/products?category=${cat_id}`);
    },
    { enabled: !!cat_id },
  );

  if (isLoading) {
    return <h2>Loading...</h2>;
  }

  return (
    <>
      <Title title="상품목록" />
      <ProductLayout cat_id={cat_id} search={search} data={data} />
    </>
  );
}
