import Link from "next/link";
import { useMoney } from "../../hooks/useMoney";
import { NBlack } from "../../typings/NormalColor";
import { ThemeRed } from "../../typings/ThemeColor";

export default function ProductInfo({ id, name, price, discount }) {
  return (
    <>
      <div className="wrapper">
        <Link href={`/product/productDetail?id=${id}`}>
          <a>
            <div style={{ marginTop: "5%" }}>
              <p
                style={{
                  fontSize: "16px",
                  fontWeight: "700",
                  color: "#3a3a3a",
                  height: "50px",
                }}
              >
                {name}
              </p>

              {discount !== 0 ? (
                <>
                  <p
                    style={{
                      fontSize: "14px",
                      fontWeight: "600",
                      color: "#898989",
                      textDecoration: "line-through",
                    }}
                  >
                    {useMoney(price)}원
                  </p>
                  <div
                    className="priceRate"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <span>
                      {useMoney(Math.ceil(price * (1 - discount * 0.01)))}원
                    </span>
                    <span style={{ color: `${ThemeRed}` }}>{discount}%</span>
                  </div>
                </>
              ) : (
                <>
                  <p
                    style={{
                      height: "19px",
                    }}
                  >
                    {/* {useMoney(price)}원 */}
                  </p>
                  <div
                    className="priceRate"
                    style={{ display: "flex", justifyContent: "space-between" }}
                  >
                    <span>{useMoney(price)}원</span>
                  </div>
                </>
              )}
            </div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        .wrapper {
          .priceRate {
            font-size: 24px;
            font-weight: 700;
            color: ${NBlack};
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
