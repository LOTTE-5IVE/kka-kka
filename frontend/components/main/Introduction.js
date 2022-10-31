import { useEffect } from "react";

export default function Introduction() {
  useEffect(() => {}, []);

  return (
    <div className="Introduction">
      <section className="btsProduct ">
        <div className="inner">
          <img className="product" src="/main/bts.png" />

          <div className="textGroup">
            <div>
              <p className="text1">BTS 얼굴이 담겨있는 한정판 껌</p>
              <p className="text2">상큼한 자일리톨 퍼플믹스</p>
              <p className="text3">
                달콤한 블루베리와 상큼한 자몽의 조화, 보라색 한정판 케이스에
                담긴 무설탕,
                <br /> 100% 핀란드산으로 더 커진 자일리톨 BTS 한정판을
                만나보세요.
              </p>
            </div>
          </div>
        </div>
      </section>

      <section className="ganaChoco ">
        <div className="inner">
          <div className="textGroup">
            <div>
              <p className="text1">가나 생쇼콜라</p>

              <p className="text2">
                가나산 카카오빈을 주원료로 사용하고 카카오버터의 함량을 높여
                <br />
                스위스풍의 부드러운 풍미를 가진, 가나초콜릿을 생쇼콜라로
                즐겨보세요.
              </p>
            </div>
          </div>

          <img className="product" src="/main/ganachoco.png" alt="" />
        </div>
      </section>

      <section className="oatmeal ">
        <div className="inner">
          <img src="/main/oatmeal_bg_texture.png" alt="" className="texture1" />

          <img className="picture picture1" src="/main/oatmeal1.png" />
          <img className="picture picture2" src="/main/oatmeal2.png" />

          <div className="textGroup">
            <div className="title">
              <p className="text1">당신의 아침을 채워줄</p>
              <p className="text2">퀘이커 오트밀</p>
              <p className="text3">
                밥보다 맛있고 든든한 아침 한 끼로, <br /> 갓 생산되어 출고된
                제품으로 더 안전하게 즐길 수 있으며,
                <br /> 맛과 영양 모두 챙길 수 있는 오트밀입니다.
              </p>
            </div>
          </div>
        </div>
      </section>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .btsProduct {
            background-image: url("/main/bts_product_bg.jpg");
            width: 1903px;

            .inner {
              width: 1100px;
              margin: 0 auto;
              position: relative;
              height: 400px;

              .product {
                width: 400px;
              }
            }

            .textGroup {
              position: absolute;
              top: 130px;
              right: 100px;

              .text1 {
                font-size: 20px;
                font-weight: 500;
                color: #404041;
                margin: 0;
              }

              .text2 {
                font-size: 32px;
                font-weight: 600;
                color: #333333;
                margin: 0;
              }

              .text3 {
                font-weight: 600;
                color: #767778;
              }
            }
          }

          .ganaChoco {
            background-image: url("/main/gana_bg.jpg");
            width: 1903px;

            .inner {
              width: 1100px;
              margin: 0 auto;
              position: relative;
              height: 400px;

              .textGroup {
                position: absolute;
                top: 150px;
                left: 130px;

                .text1 {
                  font-size: 30px;
                  font-weight: 500;
                  color: #fff;
                  margin: 0;
                }

                .text2 {
                  font-weight: 600;
                  color: #a9866e;
                }
              }
            }

            .product {
              width: 400px;
              position: absolute;
              top: 0;
              right: 0;
            }
          }

          .oatmeal {
            background-image: url("/main/oatmeal_bg.jpg");
            width: 1903px;

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
                width: 350px;
                top: -60px;
                left: 0;
              }

              .picture2 {
                width: 216px;
                top: 105px;
                left: 200px;
              }

              .textGroup {
                position: absolute;
                top: 120px;
                left: 550px;

                .title {
                  margin-bottom: 20px;

                  .text1 {
                    font-weight: 600;
                    color: #5d5d5d;
                    margin: 0;
                  }

                  .text2 {
                    font-size: 30px;
                    font-weight: 600;
                    color: #333333;
                    margin: 0;
                  }

                  .text3 {
                    font-weight: 600;
                    color: #5d5d5d;
                  }
                }
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          .btsProduct {
            background-image: url("/main/bts_product_bg.jpg");
            width: 100vw;
            min-height: 150px;

            .inner {
              width: 70vw;
              height: 21.05vw;
              margin: 0 auto;
              position: relative;

              .product {
                width: 21.05vw;
              }
            }

            .textGroup {
              position: absolute;
              top: 5vw;
              right: 5.26vw;
              width: 40vw;
              height: 12vw;

              .text1 {
                font-size: 1.05vw;
                font-weight: 500;
                color: #404041;
                margin: 0;
              }

              .text2 {
                font-size: 1.68vw;
                font-weight: 600;
                color: #333333;
                margin: 0;
              }

              .text3 {
                font-size: 0.84vw;
                font-weight: 600;
                color: #767778;
              }
            }
          }

          .ganaChoco {
            background-image: url("/main/gana_bg.jpg");
            width: 100vw;
            min-height: 150px;

            .inner {
              width: 70vw;
              margin: 0 auto;
              position: relative;
              height: 21.05vw;

              .textGroup {
                width: 40vw;

                position: absolute;
                top: 7vw;
                left: 6.84vw;

                .text1 {
                  font-size: 1.58vw;
                  font-weight: 500;
                  color: #fff;
                  margin: 0;
                }

                .text2 {
                  font-size: 0.84vw;
                  font-weight: 600;
                  color: #a9866e;
                }
              }
            }

            .product {
              width: 21.05vw;
              position: absolute;
              top: 0;
              right: 0;
            }
          }

          .oatmeal {
            background-image: url("/main/oatmeal_bg.jpg");
            width: 100vw;
            min-height: 150px;

            .inner {
              width: 70vw;
              height: 21.05vw;
              margin: 0 auto;
              position: relative;

              .texture1 {
                position: absolute;
                top: 0;
                left: 21.05vw;
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
                width: 18.4vw;
                top: -3.16vw;
                left: 0vw;
              }

              .picture2 {
                width: 11.37vw;
                top: 5.53vw;
                left: 10.53vw;
              }

              .textGroup {
                position: absolute;
                top: 6.32vw;
                left: 28.94vw;
                width: 40vw;

                .title {
                  margin-bottom: 1.05vw;

                  .text1 {
                    font-weight: 600;
                    color: #5d5d5d;
                    margin: 0;
                  }

                  .text2 {
                    font-size: 1.58vw;
                    font-weight: 600;
                    color: #333333;
                    margin: 0;
                  }

                  .text3 {
                    font-size: 0.84vw;
                    font-weight: 600;
                    color: #5d5d5d;
                  }
                }
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .Introduction {
            overflow: hidden;

            .btsProduct {
              background-image: url("/main/bts_product_bg.jpg");
              width: 480px;

              .inner {
                width: 440px;
                margin: 0 auto;
                position: relative;
                height: 300px;

                .product {
                  width: 200px;
                  position: absolute;
                  left: 20%;
                  top: 25%;
                  transform: translate(-50%, -50%);
                }
              }

              .textGroup {
                width: 380px;
                position: absolute;
                left: 45%;
                top: 70%;
                transform: translate(-50%, -50%);

                .text1 {
                  font-size: 20px;
                  font-weight: 500;
                  color: #404041;
                  margin: 0;
                }

                .text2 {
                  font-size: 32px;
                  font-weight: 600;
                  color: #333333;
                  margin: 0;
                }

                .text3 {
                  font-weight: 600;
                  color: #767778;
                }
              }
            }

            .ganaChoco {
              background-image: url("/main/gana_bg.jpg");
              width: 480px;

              .inner {
                width: 440px;
                margin: 0 auto;
                position: relative;
                height: 300px;

                .product {
                  width: 200px;
                  position: absolute;
                  left: 70%;
                  top: 35%;
                  transform: translate(-50%, -50%);
                }

                .textGroup {
                  width: 380px;
                  position: absolute;
                  left: 45%;
                  top: 70%;
                  transform: translate(-50%, -50%);

                  .text1 {
                    font-size: 30px;
                    font-weight: 500;
                    color: #fff;
                    margin: 0;
                  }

                  .text2 {
                    font-weight: 600;
                    color: #a9866e;
                  }
                }
              }
            }

            .oatmeal {
              background-image: url("/main/oatmeal_bg.jpg");
              width: 480px;

              .inner {
                width: 440px;
                height: 300px;
                margin: 0 auto;
                position: relative;

                .texture1 {
                  position: absolute;
                  top: 0;
                  left: 100px;
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
                  width: 200px;
                  position: absolute;
                  left: 15%;
                  top: 25%;
                  transform: translate(-50%, -50%);
                }

                .picture2 {
                  width: 140px;
                  position: absolute;
                  left: 25%;
                  top: 40%;
                  transform: translate(-50%, -50%);
                }

                .textGroup {
                  width: 380px;
                  position: absolute;
                  left: 80%;
                  top: 70%;
                  transform: translate(-50%, -50%);

                  .title {
                    margin-bottom: 20px;

                    .text1 {
                      font-weight: 600;
                      color: #5d5d5d;
                      margin: 0;
                    }

                    .text2 {
                      font-size: 30px;
                      font-weight: 600;
                      color: #333333;
                      margin: 0;
                    }

                    .text3 {
                      font-weight: 600;
                      color: #5d5d5d;
                    }
                  }
                }
              }
            }
          }
        }
      `}</style>
    </div>
  );
}
