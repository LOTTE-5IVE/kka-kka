import axios from "axios";
import { useEffect, useState } from "react";
import { useMoney } from "../../hooks/useMoney";

export default function ApplyProduct() {
  const [products, setProducts] = useState();

  const cat_name = {
    0: "전체 상품",
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

  const getProducts = async () => {
    await axios.get("/api/products?category=0").then((res) => {
      console.log(res.data);
      console.log(res.data.data);

      setProducts(res.data.data);
    });
  };

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <>
      <div className="title">
        <div className="check">
          <p style={{ width: "90px" }}>
            <input type="checkbox" />
          </p>
        </div>
        <div>
          <p style={{ width: "70px" }}>이미지</p>
        </div>
        <div>
          <p style={{ width: "300px" }}>상품명</p>
        </div>
        <div>
          <p style={{ width: "150px" }}>카테고리</p>
        </div>
        <div>
          <p style={{ width: "100px" }}>가격</p>
        </div>
      </div>
      {products?.map((product) => {
        return (
          <div className="outter">
            <div className="check">
              <ul style={{ width: "90px" }}>
                <li>
                  <input type="checkbox" />
                </li>
              </ul>
            </div>
            <div>
              <ul style={{ width: "70px" }}>
                <li>
                  <img width="60px" src={product.image_url} />
                </li>
              </ul>
            </div>
            <div>
              <ul style={{ width: "300px" }}>
                <li>{product.name}</li>
              </ul>
            </div>
            <div>
              <ul style={{ width: "150px" }}>
                <li>{product.category.name}</li>
              </ul>
            </div>
            <div>
              <ul style={{ width: "100px" }}>
                <li>{useMoney(product.price)}원</li>
              </ul>
            </div>
          </div>
        );
      })}

      <style jsx>{`
        .title {
          display: flex;

          p {
            border: 1px solid;
            margin: 0;
            text-align: center;
          }
        }

        .outter {
          display: flex;

          p {
            width: 192px;
            border: 1px solid;
            margin: 0;
            text-align: center;
          }

          ul {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 70px;
            margin: 0;
            padding: 0;
            list-style: none;
            border: 1px solid;
            text-align: center;

            li {
              border-bottom: 1px solid #dedede;
            }

            li:last-child {
              border: 0;
            }
          }
        }
      `}</style>
    </>
  );
}
