import { useEffect, useState } from "react";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Navigation, Pagination, Keyboard } from "swiper";

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import RecommCard from "./RecommCard";
import { getToken } from "../../hooks/getToken";
import axios from "axios";

export default function RecommSlider({ tab, handleTab }) {
  const [recommToggle, setRecommToggle] = useState(true);
  const [reviewToggle, setReviewToggle] = useState(true);
  const [recommendProducts, setRecommendProducts] = useState([]);
  const [reviewProducts, setReviewProducts] = useState([]);

  const SLIDE_SIZE = 5;

  function handleRecToggle() {
    if (tab === "맞춤" && recommToggle) {
      setRecommToggle(false);
    } else {
      setRecommToggle(true);
    }
  }

  function handleRevToggle() {
    if (tab === "리뷰" && reviewToggle) {
      setReviewToggle(false);
    } else {
      setReviewToggle(true);
    }
  }

  const getRecommendProducts = async () => {
    await axios
      .get(`/api/products/recommend?size=${SLIDE_SIZE}`, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      })
      .then((res) => {
        setRecommendProducts(res.data.data);
        return res;
      })
      .catch(function (error) {
        console.log(error);
        return {};
      });
  };

  const getReviewProducts = async () => {
    await axios
      .get(`/api/products?sortBy=BEST&size=${SLIDE_SIZE}`)
      .then((res) => {
        setReviewProducts(res.data.data);
      });
  };

  useEffect(() => {
    getRecommendProducts();
    getReviewProducts();
  }, [recommToggle, reviewToggle]);

  return (
    <>
      <section className="notice">
        <div className="notice-line">
          <div className="inner" style={{ cursor: "pointer" }}>
            <div
              className="inner__left"
              onClick={() => {
                handleTab("맞춤");
                handleRecToggle();
              }}
            >
              <h2>맞춤 추천</h2>
              <div className="toggle-promotion">
                <ArrowDropDownIcon style={{ color: "#fff" }} />
              </div>
            </div>
            <div
              className="inner__right"
              onClick={() => {
                handleTab("리뷰");
                handleRevToggle();
              }}
            >
              <h2>별점 추천</h2>
              <div className="toggle-promotion">
                <ArrowDropDownIcon />
              </div>
            </div>
          </div>
        </div>

        {tab === "맞춤" && (
          <>
            <div className={recommToggle ? "promotion" : "promotion hide"}>
              <div className="swiper-container">
                <Swiper
                  className="swiper-wrapper"
                  loop={true}
                  spaceBetween={10}
                  slidesPerView={SLIDE_SIZE}
                  navigation={true}
                  pagination={{ clickable: true }}
                  keyboard={true}
                  autoplay={{
                    delay: 2500,
                  }}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                >
                  <>
                    {recommendProducts.map((product, idx) => {
                      return (
                        <SwiperSlide key={idx} className="swiper-slide">
                          <RecommCard
                            id={product.id}
                            name={product.name}
                            imgsrc={product.imageUrl}
                            price={product.price}
                            discount={product.discount}
                          />
                        </SwiperSlide>
                      );
                    })}
                  </>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
            </div>
          </>
        )}
        {tab === "리뷰" && (
          <>
            <div className={reviewToggle ? "promotion" : "promotion hide"}>
              <div className="swiper-container">
                <Swiper
                  className="swiper-wrapper"
                  loop={true}
                  spaceBetween={10}
                  slidesPerView={SLIDE_SIZE}
                  navigation={true}
                  pagination={{ clickable: true }}
                  keyboard={true}
                  autoplay={{
                    delay: 2500,
                  }}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                >
                  <>
                    {reviewProducts.map((product, idx) => {
                      return (
                        <SwiperSlide key={idx} className="swiper-slide">
                          <RecommCard
                            id={product.id}
                            name={product.name}
                            imgsrc={product.imageUrl}
                            price={product.price}
                            discount={product.discount}
                          />
                        </SwiperSlide>
                      );
                    })}
                  </>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
            </div>
          </>
        )}
      </section>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .notice {
            .notice-line {
              position: relative;

              .inner {
                width: 1903px;
                margin: 0 auto;
                position: relative;
                height: 62px;
                z-index: 1;
                display: flex;

                .inner__left,
                .inner__right {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  width: 951.5px;
                  height: 62px;

                  h2 {
                    font-size: 17px;
                    font-weight: 700;
                  }

                  .toggle-promotion {
                    width: 62px;
                    height: 62px;
                    cursor: pointer;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  }
                }

                .inner__left {
                  background-color: #333;

                  h2 {
                    color: #fff;
                  }
                }
              }
            }
          }

          .notice .promotion {
            width: 1903px;
            height: 600px;
            background-color: #f6f5ef;
            position: relative;
            transition: height 0.4s;
            overflow: hidden;

            .swiper-container {
              width: 2400px;
              height: 470px;
              position: absolute;
              top: 40px;
              left: 50%;
              transform: translate(-50%, 0%);
            }

            .swiper-slide {
              opacity: 0.5;
              transition: opacity 1s;
              position: relative;
            }

            .swiper-slide-active {
              opacity: 1;
            }

            .swiper-pagination {
              bottom: 40px;
              left: 0;
              right: 0;
            }
          }
          .notice .promotion.hide {
            height: 0;
          }
        }

        @media screen and (max-width: 768px) {
          .notice {
            .notice-line {
              position: relative;

              .inner {
                width: 100vw;
                margin: 0 auto;
                position: relative;
                height: 3.26vw;
                z-index: 1;
                display: flex;

                .inner__left,
                .inner__right {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  width: 50.1vw;
                  height: 3.26vw;

                  h2 {
                    font-size: 0.9vw;
                    font-weight: 700;
                  }

                  .toggle-promotion {
                    width: 3.26vw;
                    height: 3.26vw;
                    cursor: pointer;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  }
                }

                .inner__left {
                  background-color: #333;

                  h2 {
                    color: #fff;
                  }
                }
              }
            }
          }

          .notice .promotion {
            width: 100vw;
            height: 33vw;
            background-color: #f6f5ef;
            position: relative;
            transition: height 0.4s;
            overflow: hidden;

            .swiper-container {
              width: 126vw;
              height: 31vw;
              position: absolute;
              top: 2.1vw;
              left: 50vw;
              transform: translate(-50%, 0%);
            }

            .swiper-slide {
              opacity: 0.5;
              transition: opacity 1s;
              position: relative;
            }

            .swiper-slide-active {
              opacity: 1;
            }

            .swiper-pagination {
              bottom: 2.1vw;
              left: 0vw;
              right: 0vw;
            }
          }
          .notice .promotion.hide {
            height: 0;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .notice {
            .notice-line {
              position: relative;

              .inner {
                width: 480px;
                margin: 0 auto;
                position: relative;
                height: 62px;
                z-index: 1;
                display: flex;

                .inner__left,
                .inner__right {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  width: 240px;
                  height: 62px;

                  h2 {
                    font-size: 17px;
                    font-weight: 700;
                  }

                  .toggle-promotion {
                    width: 62px;
                    height: 62px;
                    cursor: pointer;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  }
                }

                .inner__left {
                  background-color: #333;

                  h2 {
                    color: #fff;
                  }
                }
              }
            }
          }

          .notice .promotion {
            width: 480px;
            height: 200px;
            background-color: #f6f5ef;
            position: relative;
            transition: height 0.4s;
            overflow: hidden;

            .swiper-container {
              width: 800px;
              height: 160px;
              position: absolute;
              top: 40px;
              left: 50%;
              transform: translate(-50%, 0%);
            }

            .swiper-slide {
              opacity: 0.5;
              transition: opacity 1s;
              position: relative;
            }

            .swiper-slide-active {
              opacity: 1;
            }

            .swiper-pagination {
              bottom: 40px;
              left: 0;
              right: 0;
            }
          }
          .notice .promotion.hide {
            height: 0;
          }
        }
      `}</style>
    </>
  );
}
