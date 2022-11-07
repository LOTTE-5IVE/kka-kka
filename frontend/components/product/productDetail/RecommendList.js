import {useEffect, useState} from "react";
import axios from "axios";
import {useRouter} from "next/router";
import ProductRecCard from "../ProductRecCard";

export default function RecommendList() {
  const router = useRouter();
  const productId = router.query.id;

  const [products, setProducts] = useState([]);

  const getRecommendProducts = async () => {
    await axios
    .get(`/api/products/${productId}/recommend`)
    .then((res) => {
      setProducts(res.data.data)
    })
  }

  useEffect(() => {
    getRecommendProducts();
  }, [])

  return (
      <>
        <div className="recommendWrapper">
          <div className="divider"></div>
          <div className="recommendText">
            다른 고객들은 이런 상품도 구매했어요.
          </div>
          <div className="productList">
            {products?.map((product) => {
              return (
                  <div className="productInner">
                    <div className="productBox">
                      <ProductRecCard
                          id={product.id}
                          name={product.name}
                          imgsrc={product.imageUrl}
                          imgSize={'200px'}
                          price={product.price}
                          discount={product.discount}
                      />
                    </div>
                  </div>
              )
            })}
          </div>
        </div>
        <style jsx>{`
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .recommendWrapper {
              padding: 0.5rem;
            }
            
            .recommendText {
              font-size: 1.3rem;
              font-weight: 700;
              margin-left: 2rem;
            }
            
            .divider {
              border-bottom: 1px solid #dedede;
              margin: 1rem 0;
            }
            
            .productList {
              display: flex;
              overflow: hidden;
              overflow-x: scroll;
              overflow-y: hidden;
              white-space: no-wrap;
              
              margin-top: 0.7rem;
              
              ::-webkit-scrollbar {
                display: none;
              }

              
              .productInner {
                margin: 1rem 2rem;

                .productBox {
                  width: 220px;
                  margin: 0 auto;
                }
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */

            .productList {
              display: table;
              width: 60vw;
              height: 82.42vw;

              .productInner {
                display: inline-block;
                width: 33.3%;
                margin-bottom: 3vw;

                .productBox {
                  width: 15.68vw;
                  margin: 0 auto;
                }
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */

            .productList {
              display: table;
              width: 480px;
              height: 750px;
              padding: 0;

              .productInner {
                display: inline-block;
                width: 33.3%;
                margin-bottom: 20px;

                .productBox {
                  width: 150px;
                  margin: 0 auto;
                }
              }
            }
          }
        `}</style>
      </>

  )
}
