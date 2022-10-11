import Link from "next/link";
import { useRouter } from "next/router";
import NavBar from "../../components/common/NavBar";
import ProductRec from "../../components/product/ProductRec";
import Sidebar from "../../components/product/Sidebar";
import { useQuery } from "react-query";
import axios from "axios";
import Title from "../../components/common/Title";
import { useEffect, useState } from "react";

export default function productCidList() {
  const [state, setState] = useState([]);
  // 카테고리 아이디별 호출
  // https://nomadcoders.co/nextjs-fundamentals/lectures/3451 7분 참고
  const router = useRouter();
  const cat_id = router.query.cat_id;
  const search = router.query.search;
  const cat_name = {
    1: "전체 상품",
    2: "비스킷/샌드",
    3: "스낵/봉지과자",
    4: "박스과자",
    5: "시리얼/바",
    6: "캔디/사탕/젤리",
    7: "초콜릿",
    8: "껌/자일리톨",
    9: "선물세트",
  };

  // const getCatItems = async () => {
  //   await axios
  //     .get(`http://localhost:9000/api/products/products?category=${cat_id}`)
  //     .then((res) => {
  //       setState(res.data);
  //     });
  // };

  // useEffect(() => {
  //   getCatItems();
  // }, [cat_id]);

  const { isLoading, data } = useQuery(["products", cat_id], () => {
    return axios.get(
      `http://localhost:9000/api/products/products?category=${cat_id}`,
    );
  });

  if (isLoading) {
    return <h2>Loading...</h2>;
  }

  return (
    <>
      <Title title="상품목록" />
      <div className="contents">
        <div className="sidebar">
          <Sidebar menu={cat_id} />
        </div>
        <div className="productWrapper">
          <div style={{ paddingLeft: "60px" }}>
            {search ? (
              <div className="searchBar">
                <img width="50px" src="/mg.png" />
                <p>
                  <input type="text" size="12" value={search} />
                </p>
              </div>
            ) : (
              <h2>{cat_name[cat_id]}</h2>
            )}
          </div>
          <ul className="productList">
            {/* {state &&
              state
                // .filter((product) => search?.product.name.includes(search))

                .map((product) => {
                  return (
                    <li className="productInner" key={product.id}>
                      <div className="productBox">
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
                })} */}
            {data?.data.map((product) => {
              return (
                <li className="productInner" key={product.id}>
                  <div className="productBox">
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

          .productWrapper {
            width: 100%;
          }

          .searchBar {
            width: 100%;
            display: flex;
            align-items: flex-start;

            p {
              width: 30%;
              margin-left: 3%;
              padding-left: 3%;
              line-height: 35px;
              border-bottom: 3px solid red;

              input[type="text"] {
                border: 0;
                font-size: 17px;
                font-weight: 600;
              }

              input[type="text"]:focus {
                outline: none;
              }
            }
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
