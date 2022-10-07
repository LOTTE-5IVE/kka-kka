import Link from "next/link";

export default function ProductRec({ id, imgsrc, name, price, rate }) {
  return (
    <>
      <div className="wrapper">
        <Link href={`/product/productDetail/${id}`}>
          <a>
            <div className="imageSection">
              <img src={imgsrc} />
            </div>
            <div style={{ marginTop: "5%" }}>
              <p
                style={{
                  fontSize: "16px",
                  fontWeight: "700",
                  color: "#3a3a3a",
                }}
              >
                {name}
              </p>
              <p
                style={{
                  fontSize: "14px",
                  fontWeight: "600",
                  color: "#898989",
                  textDecoration: "line-through",
                }}
              >
                {price}원
              </p>
              <div
                className="priceRate"
                style={{ display: "flex", justifyContent: "space-between" }}
              >
                <span>{price * (1 - rate * 0.01)}원</span>
                <span style={{ color: "#f11c2b" }}>{rate}%</span>
              </div>
            </div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        .wrapper {
          .imageSection {
            text-align: center;
            border-radius: 20px;
            background-color: #f5f5f5;

            img {
              max-height: 300px;
              height: 16vw;
            }
          }

          .priceRate {
            font-size: 24px;
            font-weight: 700;
            color: #3a3a3a;
          }
        }
      `}</style>
    </>
  );
}

ProductRec.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
  name: "test",
  price: 10000,
  rate: 10,
};
