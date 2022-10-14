import Link from "next/link";
import { useRouter } from "next/router";
import NavBar from "../components/common/NavBar";
import ProductRec from "../components/product/ProductRec";
import Sidebar from "../components/product/Sidebar";
import { useQuery } from "react-query";
import axios from "axios";

export default function productCidList() {
  const { isLoading, data } = useQuery("products", () => {
    return axios.get("http://localhost:4000/products");
  });

  if (isLoading) {
    return <h2>Loading...</h2>;
  }
  // 카테고리 아이디별 호출
  // https://nomadcoders.co/nextjs-fundamentals/lectures/3451 7분 참고
  const router = useRouter();
  const q_test = (cat_id) => {
    router.push({
      pathname: `/product/`,
      query: {
        cat_id,
      },
    });
  };
  console.log(router.query);

  return (
    <>
      <div className="contents">
        <div className="sidebar">
          카테고리 아이디 : {router.query.cid}
          <Sidebar />
        </div>
        <div>
          {data?.data.map((product) => {
            return (
              <div key={product.id}>
                <img src={product.imageUrl} />
              </div>
            );
          })}
        </div>
        <ul className="productList">
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
          <li className="productInner">
            <div className="productBox">
              <ProductRec />
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        .contents {
          margin: 0 auto;
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
