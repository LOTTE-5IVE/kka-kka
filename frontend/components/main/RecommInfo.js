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
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            .priceRate {
              font-size: 50px;
              font-weight: 700;
              color: ${NBlack};
              margin: 0;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            .priceRate {
              font-size: 25px;
              font-weight: 700;
              color: ${NBlack};
              margin: 0;
            }
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
