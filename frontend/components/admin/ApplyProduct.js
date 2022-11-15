import axios from "axios";
import { useEffect, useState } from "react";
import { commaMoney } from "../../hooks/commaMoney";
import Pagination from "../product/Pagination";

export default function ApplyProduct({ productId, setProductId }) {
  const [products, setProducts] = useState();
  const [page, setPage] = useState(1);
  const [lastPage, setLastPage] = useState();

  const getProducts = async () => {
    await axios.get(`/api/products?category=0&page=${page}`).then((res) => {
      setProducts(res.data.data);
      setLastPage(res.data.pageInfo.lastPage);
    });
  };

  useEffect(() => {
    getProducts();
  }, [page]);

  return (
    <div className="wrapper">
      <div className="title">
        <div className="check">
          <p style={{ width: "90px" }}>선택</p>
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
          <div className="outter" key={product.id}>
            <div className="check">
              <ul style={{ width: "90px" }}>
                <li>
                  <input
                    type="radio"
                    value={product.id}
                    checked={productId == product.id}
                    onChange={(e) => {
                      setProductId(e.target.value);
                    }}
                  />
                </li>
              </ul>
            </div>
            <div>
              <ul style={{ width: "70px" }}>
                <li>
                  <img width="60px" src={product.imageUrl} alt="" />
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
                <li>{commaMoney(product.price)}원</li>
              </ul>
            </div>
          </div>
        );
      })}

      <div className="pagination" style={{ width: "710px", display: "flex" }}>
        <Pagination page={page} setPage={setPage} lastPage={lastPage} />
      </div>

      <style jsx>{`
        .wrapper {
          width: 730px;
          overflow: auto;
          height: 16vw;
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
        }
      `}</style>
    </div>
  );
}
