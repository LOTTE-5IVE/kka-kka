import Link from "next/link";
import {NBlack} from "../../typings/NormalColor";
import {ThemeRed} from "../../typings/ThemeColor";
import {useMoney} from "../../hooks/useMoney";

export default function RecommInfo({id, name, price, discount}) {
  return (
      <>
        <div className="wrapper">
          <Link href={`/product/productDetail?id=${id}`}>
            <a>
              <div style={{marginTop: "5%"}}>
                <p
                    className="name"
                >
                  {name}
                </p>
                {discount !== 0 ? (
                    <>
                      <div
                          className="priceRate"
                          style={{
                            display: "flex",
                            justifyContent: "space-between"
                          }}
                      >
                        <div style={{
                          width: "100%",
                          display: "flex",
                          flexDirection: "column",
                          alignItems: "flex-start"
                        }}>
                          <span className="discount">{useMoney(price)}원</span>
                          <span>
                        {useMoney(Math.ceil(price * (1 - discount * 0.01)))}원
                          </span>
                        </div>
                        <span style={{color: `${ThemeRed}`}}>{discount}%</span>
                      </div>
                    </>
                ) : (
                    <>
                      <div
                          className="priceRate"
                          style={{
                            display: "flex",
                            justifyContent: "space-between"
                          }}
                      >
                        <div style={{
                          width: "100%",
                          display: "flex",
                          flexDirection: "column",
                          alignItems: "flex-start"
                        }}>
                          <span className="blank"></span>
                          <span>{useMoney(price)}원</span>
                        </div>
                      </div>
                    </>
                )}
              </div>
            </a>
          </Link>
        </div>

        <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            p {
              margin: 16px 0;
            }

            margin-top: 15px;
            .name {
              font-size: 1.2rem;
              font-weight: 700;
              color: #3a3a3a;
              height: 50px;
            }

            .discount {
              font-size: 14px;
              font-weight: 600;
              color: #898989;
              text-decoration: line-through;
            }

            .blank {
              height: 19px;
            }
          }
          
          .priceRate {
            font-size: 1.3rem;
            font-weight: 700;
            color: ${NBlack};
            padding: 0 7rem
          }
        }

        @media screen and (max-width: 768px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            p {
              margin: 16px 0 8px 0;
            }

            margin-top: 15px;
            .name {
              font-size: 2.63vw;
              font-weight: 700;
              color: #3a3a3a;
            }

            .discount {
              font-size: 2.0vw;
              font-weight: 600;
              color: #898989;
              text-decoration: line-through;
            }

            .blank {
              height: 10px;
            }
          }
          
          .priceRate {
            font-size: 2.63vw;
            font-weight: 700;
            color: ${NBlack};
            margin: 0;
            padding: 0 4vw;
          }
        }
      `}</style>
      </>
  );
}

RecommInfo.defaultProps = {
  id: 1,
  name: "샘플",
  price: 10000,
  discount: 10
};
