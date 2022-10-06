// Import Swiper React components
import { Swiper, SwiperSlide } from "swiper/react";

// import required modules
import SwiperCore, {
  Autoplay,
  Navigation,
  Pagination,
  Mousewheel,
  Keyboard,
} from "swiper";

// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

export default function App() {
  SwiperCore.use([Autoplay, Navigation, Pagination]);

  return (
    <>
      <Swiper
        loop={true} // 슬라이드 반복 여부
        slidesPerView={1} // 한 슬라이드에 보여줄 갯수
        cssMode={true}
        navigation={true}
        autoplay={{
          delay: 2500,
        }}
        pagination={true}
        keyboard={true}
        modules={[Autoplay, Navigation, Pagination, Keyboard]}
        className="mySwiper"
        style={{ height: "19vw" }}
      >
        <SwiperSlide
          style={{
            textAlign: "center",
            position: "relative",
            display: "block",
            margin: "0 auto",
          }}
        >
          <img src="/main/1.png" />
        </SwiperSlide>
        <SwiperSlide className="swiper-slide">
          <img src="/main/2.png" />
        </SwiperSlide>
      </Swiper>

      <style jsx>{`
        img {
          max-height: 360px;
          height: 20vw;
        }

        .swiper-slide {
          text-align: center;
          position: relative;
          display: block;
          margin: 0 auto;
        }
      `}</style>
    </>
  );
}
