import Link from "next/link";
import { NBlack } from "../../typings/NormalColor";
import { ThemeRed } from "../../typings/ThemeColor";

export default function RecommInfo({ id, name }) {
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
            </div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        .wrapper {
          .priceRate {
            font-size: 50px;
            font-weight: 700;
            color: ${NBlack};
            margin: 0;
          }
        }
      `}</style>
    </>
  );
}

RecommInfo.defaultProps = {
  id: 1,
  name: "샘플",
};
