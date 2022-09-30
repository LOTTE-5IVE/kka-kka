import Link from "next/link";
import { useRouter } from "next/router";
import NavBar from "../../components/common/NavBar";
import ProductRec from "../../components/product/ProductRec";
import Sidebar from "../../components/product/Sidebar";
import { useQuery } from "react-query";
import axios from "axios";

export default function productCidList() {
  const { isLoading, data } = useQuery("products", () => {
    return axios.get(`http://localhost:4000/products`);
  });

  if (isLoading) {
    return <h2>Loading...</h2>;
  }
  // 카테고리 아이디별 호출
  // https://nomadcoders.co/nextjs-fundamentals/lectures/3451 7분 참고
  const router = useRouter();
  const cat_id = router.query.cat_id;
  const cat_name = { 1: "전체 상품", 2: "비스킷/샌드" };

  return (
    <>
      <div className="contents">
        <div className="sidebar">
          {/* 카테고리 아이디 : {cat_id} */}
          <Sidebar />
        </div>
        <div className="productWrapper">
          <div style={{ paddingLeft: "60px" }}>
            <h2>{cat_name[cat_id]}</h2>
          </div>
          <ul className="productList">
            {data?.data
              .filter((product) => product.cat_id == cat_id)
              .map((product) => {
                return (
                  <li className="productInner">
                    <div className="productBox" key={product.id}>
                      <ProductRec
                        id={product.id}
                        imgsrc={product.imageUrl}
                        name={product.name}
                        price={product.price}
                        rate="10"
                      />
                    </div>
                  </li>
                );
              })}
          </ul>
        </div>
      </div>

      <style jsx>{`
        .contents {
          margin: 3% auto 0;
          width: 70%;
          display: flex;
          .sidebar {
            display: inline-block;
            width: 23%;
            margin-right: 5%;
          }

          .productList {
            display: table;
            width: 100%;
            .productInner {
              display: inline-block;
              width: 33.33%;
              margin-bottom: 7%;

              .productBox {
                width: 90%;
                margin: 0 auto;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
