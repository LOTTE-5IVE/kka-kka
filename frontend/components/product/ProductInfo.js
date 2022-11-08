import Link from "next/link";
import { commaMoney } from "../../hooks/commaMoney";
import { NBlack } from "../../typings/NormalColor";
import { ThemeRed } from "../../typings/ThemeColor";

export default function ProductInfo({ id, name, price, discount }) {
  return (
    <>
      <div className="ProductInfoWrapper">
        <Link href={`/product/productDetail?id=${id}`}>
          <a>
            <div className="text">
              <p className="name">{name}</p>

              {discount !== 0 ? (
                <>
                  <p className="discount">{commaMoney(price)}원</p>
                  <div
                    className="priceRate"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <span>
                      {commaMoney(Math.ceil(price * (1 - discount * 0.01)))}원
                    </span>
                    <span style={{ color: `${ThemeRed}` }}>{discount}%</span>
                  </div>
                </>
              ) : (
                <>
                  <p className="blank"></p>
                  <div
                    className="priceRate"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <span>{commaMoney(price)}원</span>
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
          .ProductInfoWrapper {
            .text {
              p {
                margin: 16px 0;
              }

              margin-top: 15px;
              .name {
                font-size: 16px;
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
              font-size: 24px;
              font-weight: 700;
              color: ${NBlack};
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductInfoWrapper {
            .text {
              p {
                margin: 8px 0;
              }

              margin-top: 7.5px;
              .name {
                font-size: 8px;
                font-weight: 700;
                color: #3a3a3a;
                height: 25px;
              }

              .discount {
                font-size: 7px;
                font-weight: 600;
                color: #898989;
                text-decoration: line-through;
              }

              .blank {
                height: 9.5px;
              }
            }

            .priceRate {
              font-size: 12px;
              font-weight: 700;
              color: ${NBlack};
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductInfoWrapper {
            .text {
              p {
                margin: 8px 0;
              }

              margin-top: 7.5px;
              .name {
                font-size: 8px;
                font-weight: 700;
                color: #3a3a3a;
                height: 25px;
              }

              .discount {
                font-size: 7px;
                font-weight: 600;
                color: #898989;
                text-decoration: line-through;
              }

              .blank {
                height: 9.5px;
              }
            }

            .priceRate {
              font-size: 12px;
              font-weight: 700;
              color: ${NBlack};
            }
          }
        }
      `}</style>
    </>
  );
}

ProductInfo.defaultProps = {
  id: 1,
  name: "test",
  price: 10000,
  rate: 10,
};
