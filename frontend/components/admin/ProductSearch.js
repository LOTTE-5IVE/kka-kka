import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useMoney } from "../../hooks/useMoney";
import { AdminButton } from "../common/Button/AdminButton";

export default function ProductSearch() {
  const [products, setProducts] = useState();

  const getProducts = async () => {
    await axios.get("/api/products?category=0").then((res) => {
      console.log(res.data.data);
      setProducts(res.data.data);
    });
  };

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "10%" }} />
            <col style={{ width: "30%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "20%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
          </colgroup>
          <thead>
            <th>대표사진</th>
            <th>상품 이름</th>
            <th>가격</th>
            <th>재고</th>
            <th>카테고리</th>
            <th>수정</th>
            <th>상품 페이지</th>
          </thead>
          <tbody>
            {products?.map((product) => {
              return (
                <tr style={{ height: "3vw" }} key={product.id}>
                  <td>
                    <img width="60px" src={product.image_url} />
                  </td>
                  <td>{product.name}</td>

                  <td>{useMoney(product.price)}원</td>
                  <td>{product.stock}</td>
                  <td>{product.category.name}</td>
                  <td>
                    <AdminButton context="수정" color="#F21D2F" />
                  </td>
                  <td>
                    <Link href={`/product/productDetail?id=${product.id}`}>
                      <a>
                        <AdminButton context="이동" color="#05C7F2" />
                      </a>
                    </Link>
                  </td>
                </tr>
              );
            })}

            <tr style={{ height: "100%" }}>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <style jsx>{`
        .contents {
          height: 100%;
          border: 2px solid #dedede;
          color: #7a7a7a;

          table {
            overflow: auto;
            height: 100%;
            width: 100%;
            text-align: center;
            border-collapse: collapse;

            td {
              border: 1px solid #dedede;
            }

            th {
              border-left: 1px solid #dedede;
            }

            th:first-child {
              border-left: 0;
            }

            td:first-child {
              border-left: 0;
            }

            td:last-child {
              border-right: 0;
            }

            tr:last-child {
              td {
                border-bottom: 0;
              }

              td:last-child {
                border-right: 0;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
