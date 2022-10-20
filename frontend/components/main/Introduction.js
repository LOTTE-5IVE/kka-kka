import { useEffect } from "react";

export default function Introduction() {
  useEffect(() => {}, []);

  return (
    <>
      <section className="bts-product ">
        <div className="inner">
          <img src="/bts.png" width="400px" alt="" className="product" />

          <div className="text-group">
            <div>
              <p
                style={{
                  fontSize: "20px",
                  fontWeight: "500",
                  color: "#404041",
                  margin: "0",
                }}
              >
                BTS 얼굴이 담겨있는 한정판 껌
              </p>
              <p
                style={{
                  fontSize: "32px",
                  fontWeight: "600",
                  color: "#333333",
                  margin: "0",
                }}
              >
                상큼한 자일리톨 퍼플믹스
              </p>
              <p style={{ fontWeight: "600", color: "#767778" }}>
                달콤한 블루베리와 상큼한 자몽의 조화, 보라색 한정판 케이스에
                담긴 무설탕,
                <br /> 100% 핀란드산으로 더 커진 자일리톨 BTS 한정판을
                만나보세요.
              </p>
            </div>
          </div>
        </div>
      </section>

      <section className="gana-choco ">
        <div className="inner">
          <div className="text-group">
            <div>
              <p
                style={{
                  fontSize: "30px",
                  fontWeight: "500",
                  color: "#fff",
                  margin: "0",
                }}
              >
                가나 생쇼콜라
              </p>

              <p style={{ fontWeight: "600", color: "#a9866e" }}>
                가나산 카카오빈을 주원료로 사용하고 카카오버터의 함량을 높여
                <br />
                스위스풍의 부드러운 풍미를 가진, 가나초콜릿을 생쇼콜라로
                즐겨보세요.
              </p>
            </div>
          </div>

          <img width="400px" src="/main4.png" alt="" className="product" />
        </div>
      </section>

      <section className="oatmeal ">
        <div className="inner">
          <img src="/main/oatmeal_bg_texture.png" alt="" className="texture1" />

          <img
            width="350px"
            src="/main5.png"
            alt=""
            className="picture picture1"
          />
          <img
            width="216px"
            src="/main6.png"
            alt=""
            className="picture picture2"
          />

          <div className="text-group">
            <div className="title">
              <p
                style={{
                  fontWeight: "600",
                  color: "#5d5d5d",
                  margin: "0",
                }}
              >
                당신의 아침을 채워줄
              </p>
              <p
                style={{
                  fontSize: "30px",
                  fontWeight: "600",
                  color: "#333333",
                  margin: "0",
                }}
              >
                퀘이커 오트밀
              </p>
              <p style={{ fontWeight: "600", color: "#5d5d5d" }}>
                밥보다 맛있고 든든한 아침 한 끼로, <br /> 갓 생산되어 출고된
                제품으로 더 안전하게 즐길 수 있으며,
                <br /> 맛과 영양 모두 챙길 수 있는 오트밀입니다.
              </p>
            </div>
          </div>
        </div>
      </section>
      <style jsx>{`
        .bts-product {
          background-image: url("/main/bts_product_bg.jpg");

          .inner {
            width: 1100px;
            margin: 0 auto;
            position: relative;
            height: 400px;
          }

          .text-group {
            position: absolute;
            top: 130px;
            right: 100px;

            .title {
              margin-bottom: 10px;
            }
          }
        }

        .gana-choco {
          background-image: url("/main/gana_bg.jpg");
          .inner {
            width: 1100px;
            margin: 0 auto;
            position: relative;
            height: 400px;

            .text-group {
              position: absolute;
              top: 150px;
              left: 130px;
            }
          }

          .product {
            position: absolute;
            top: 0;
            right: 0;
          }
        }

        .oatmeal {
          background-image: url("/main/oatmeal_bg.jpg");
          .inner {
            height: 400px;
            width: 1100px;
            margin: 0 auto;
            position: relative;

            .texture1 {
              position: absolute;
              top: 0;
              left: 400px;
            }

            .texture2 {
              position: absolute;
              bottom: 0;
              right: 0;
            }

            .picture {
              position: absolute;
            }

            .picture1 {
              top: -60px;
              left: 0;
            }

            .picture2 {
              top: 105px;
              left: 200px;
            }

            .text-group {
              position: absolute;
              top: 120px;
              left: 550px;

              .title {
                margin-bottom: 20px;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
