import Sidebar from "../components/product/Sidebar";
import ProductRecCard from "../components/product/ProductRecCard";
import { useMoney } from "../hooks/useMoney";

export default function ProductLayout({ cat_id, search, data }) {
  const cat_name = {
    0: "전체 상품",
    1: "비스킷/샌드",
    2: "스낵/봉지과자",
    3: "박스과자",
    4: "캔디/사탕/젤리",
    5: "시리얼/바",
    6: "초콜릿",
    7: "껌/자일리톨",
    8: "선물세트",
  };

  return (
    <>
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
            {data?.data.data.map((product) => {
              return (
                <li className="productInner" key={product.id}>
                  <div className="productBox">
                    <ProductRecCard
                      id={product.id}
                      imgsrc={product.image_url}
                      name={product.name}
                      price={product.price}
                      discount={product.discount}
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
