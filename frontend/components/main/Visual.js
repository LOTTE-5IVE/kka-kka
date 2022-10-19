import { useLayoutEffect, useRef } from "react";
import { gsap } from "gsap";

export default function Visual() {
  const fadeEls = useRef([]);

  useLayoutEffect(() => {
    fadeEls.current.map((fadeEl, index) => {
      gsap.to(fadeEl, 1, {
        delay: (index + 1) * 0.7,
        opacity: 1,
      });
    });
  });

  return (
    <>
      <section className="visual">
        <div className="inner">
          <div
            className="title fade-in"
            ref={(el) => (fadeEls.current[0] = el)}
          >
            <img width="350px" src="/main/logo.png" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[1] = el)}>
            <img src="/main1.png" className="cup1 image" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[2] = el)}>
            <img width="500px" src="/main2.png" className="cup2 image" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[3] = el)}>
            <img width="420px" src="/main3.png" alt="Spoon" className="spoon" />
          </div>
          <div className="fade-in" ref={(el) => (fadeEls.current[4] = el)}>
            <img src="/images/visual_cup2_text.png" className="cup2 text" />
          </div>
        </div>
      </section>

      <style jsx>{`
        .visual {
          background-image: url("/images/visual_bg.jpg");
          background-position: center;
        }
        .visual .inner {
          width: 1100px;
          margin: 0 auto;
          position: relative;
          height: 646px;
        }
        .visual .title {
          position: absolute;
          top: 15px;
          left: -80px;
        }
        .visual .title .btn {
          position: absolute;
          top: 259px;
          left: 173px;
        }
        .visual .cup1.image {
          position: absolute;
          bottom: 15px;
          right: -200px;
        }
        .visual .cup1.text {
          position: absolute;
          top: 38px;
          right: 171px;
        }
        .visual .cup2.image {
          position: absolute;
          bottom: 0;
          right: 105px;
        }
        .visual .cup2.text {
          position: absolute;
          top: 80px;
          right: 416px;
        }
        .visual .spoon {
          position: absolute;
          bottom: -10px;
          left: 280px;
        }
        .visual .fade-in {
          opacity: 0;
        }
      `}</style>
    </>
  );
}
