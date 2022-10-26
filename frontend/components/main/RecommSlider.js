import { useRef, useState } from "react";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay, Navigation, Pagination, Keyboard } from "swiper";

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import RecommCard from "./RecommCard";

export default function RecommSlider({ tab, handleTab }) {
  SwiperCore.use([Autoplay, Navigation, Pagination]);
  const [swiper, setSwiper] = useState(null);
  const navigationPrevRef = useRef(null);
  const navigationNextRef = useRef(null);
  const [recommToggle, setRecommToggle] = useState(true);
  const [reviewToggle, setReviewToggle] = useState(true);

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

  return (
    <>
      <section className="notice">
        <div className="notice-line">
          <div className="inner">
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
              <h2>리뷰 추천</h2>
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
                  centeredSlides={true}
                  slidesPerView={5}
                  cssMode={true}
                  navigation={{
                    prevEl: navigationPrevRef.current,
                    nextEl: navigationNextRef.current,
                  }}
                  onInit={(swiper) => {
                    swiper.params.navigation.prevEl = navigationPrevRef.current;
                    swiper.params.navigation.nextEl = navigationNextRef.current;

                    swiper.navigation.init();
                    swiper.navigation.update();
                  }}
                  onSwiper={setSwiper}
                  autoplay={{
                    delay: 2500,
                  }}
                  pagination={true}
                  keyboard={true}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                  ref={setSwiper}
                >
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
              <div className="swiper-prev">
                <ArrowBackIcon ref={navigationPrevRef} />
              </div>
              <div className="swiper-next">
                <ArrowForwardIcon ref={navigationNextRef} />
              </div>
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
                  spaceBetween={20}
                  centeredSlides={true}
                  slidesPerView={5} // 한 슬라이드에 보여줄 갯수
                  cssMode={true}
                  navigation={{
                    prevEl: navigationPrevRef.current,
                    nextEl: navigationNextRef.current,
                  }}
                  onInit={(swiper) => {
                    swiper.params.navigation.prevEl = navigationPrevRef.current;
                    swiper.params.navigation.nextEl = navigationNextRef.current;

                    swiper.navigation.init();
                    swiper.navigation.update();
                  }}
                  onSwiper={setSwiper}
                  autoplay={{
                    delay: 2500,
                  }}
                  pagination={true}
                  keyboard={true}
                  modules={[Autoplay, Navigation, Pagination, Keyboard]}
                  ref={setSwiper}
                >
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                  <SwiperSlide className="swiper-slide">
                    <RecommCard />
                  </SwiperSlide>
                </Swiper>
              </div>

              <div className="swiper-pagination"></div>
              <div className="swiper-prev">
                <ArrowBackIcon ref={navigationPrevRef} />
              </div>
              <div className="swiper-next">
                <ArrowForwardIcon ref={navigationNextRef} />
              </div>
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

            .swiper-prev,
            .swiper-next {
              width: 42px;
              height: 42px;
              border: 2px solid #333;
              border-radius: 50%;
              position: absolute;
              top: 300px;
              z-index: 1;
              cursor: pointer;
              outline: none;
              display: flex;
              justify-content: center;
              align-items: center;
              transition: 0.4s;
            }

            .swiper-prev {
              left: 35%;
            }
            .swiper-next {
              right: 35%;
            }
            .swiper-prev:hover,
            .swiper-next:hover {
              background-color: #333;
              color: #fff;
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
                width: 951.5px;
                margin: 0 auto;
                position: relative;
                height: 31px;
                z-index: 1;
                display: flex;

                .inner__left,
                .inner__right {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  width: 476px;
                  height: 31px;

                  h2 {
                    font-size: 8.5px;
                    font-weight: 700;
                  }

                  .toggle-promotion {
                    width: 31px;
                    height: 31px;
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
            width: 951.5px;
            height: 300px;
            background-color: #f6f5ef;
            position: relative;
            transition: height 0.4s;
            overflow: hidden;

            .swiper-container {
              width: 1200px;
              height: 235px;
              position: absolute;
              top: 20px;
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
              bottom: 20px;
              left: 0;
              right: 0;
            }

            .swiper-prev,
            .swiper-next {
              width: 21px;
              height: 21px;
              border: 1px solid #333;
              border-radius: 50%;
              position: absolute;
              top: 150px;
              z-index: 1;
              cursor: pointer;
              outline: none;
              display: flex;
              justify-content: center;
              align-items: center;
              transition: 0.4s;
            }

            .swiper-prev {
              left: 35%;
            }
            .swiper-next {
              right: 35%;
            }
            .swiper-prev:hover,
            .swiper-next:hover {
              background-color: #333;
              color: #fff;
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
            height: 300px;
            background-color: #f6f5ef;
            position: relative;
            transition: height 0.4s;
            overflow: hidden;

            .swiper-container {
              width: 800px;
              height: 260px;
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

            .swiper-prev,
            .swiper-next {
              display: none;
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
