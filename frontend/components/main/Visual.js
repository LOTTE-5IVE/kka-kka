import { useLayoutEffect, useRef } from "react";
import { gsap } from "gsap";
import Image from "next/image";

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
        <div className="fade-in" ref={(el) => (fadeEls.current[4] = el)}>
          <div className="logoComment">
            <Image src="/main/intro.png" alt="" layout="fill" />
          </div>
        </div>

        <div
          className="fade-in giftset"
          ref={(el) => (fadeEls.current[1] = el)}
        >
          <Image src="/main/giftset.png" alt="" layout="fill" />
        </div>
        <div className="fade-in cookie" ref={(el) => (fadeEls.current[2] = el)}>
          <Image src="/main/cookie.png" alt="" layout="fill" />
        </div>
        <div className="fade-in bts" ref={(el) => (fadeEls.current[3] = el)}>
          <Image src="/main/bts.png" alt="" layout="fill" />
        </div>
      </section>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .visual {
            width: 1903px;
            height: 646px;
            position: relative;

            background-image: url("/main/visual_bg.jpg");
            background-position: center;

            .logoComment {
              position: absolute;
              top: 30px;
              left: 130px;

              width: 730px;
              height: 320px;
            }

            .bts {
              width: 420px;
              height: 420px;
              position: absolute;
              bottom: 0px;
              left: 805px;
            }

            .cookie {
              width: 500px;
              height: 500px;
              position: absolute;
              bottom: 0px;
              right: 380px;
            }

            .giftset {
              width: 600px;
              height: 600px;
              position: absolute;
              bottom: 20px;
              right: 75px;
            }

            .fade-in {
              opacity: 0;
            }
          }
        }

        @media screen and (max-width: 768px) {
          .visual {
            width: 100vw;
            height: 34vw;
            position: relative;

            background-image: url("/main/visual_bg.jpg");
            background-position: center;

            .logoComment {
              position: absolute;
              top: 1.58vw;
              left: 6.73vw;

              width: 44.4vw;
              height: 19.5vw;
            }

            .bts {
              width: 22.1vw;
              height: 22.1vw;
              position: absolute;
              bottom: 0vw;
              left: 42.37vw;
            }

            .cookie {
              width: 26.31vw;
              height: 26.31vw;
              position: absolute;
              bottom: 0vw;
              right: 20vw;
            }

            .giftset {
              width: 31.58vw;
              height: 31.58vw;
              position: absolute;
              bottom: 1.05vw;
              right: 3.95vw;
            }

            .fade-in {
              opacity: 0;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .visual {
            width: 480px;
            height: 600px;
            position: relative;
            overflow: hidden;
            background-image: url("/main/visual_bg.jpg");
            background-position: center;

            .logoComment {
              position: absolute;
              left: 52%;
              top: 25%;
              transform: translate(-50%, -50%);

              width: 510px;
              height: 224px;
            }

            .bts {
              width: 200px;
              height: 200px;
              position: absolute;
              left: 25%;
              top: 75%;
              transform: translate(-50%, -50%);
            }

            .cookie {
              width: 280px;
              height: 280px;
              position: absolute;
              left: 50%;
              top: 70%;
              transform: translate(-50%, -50%);
            }

            .giftset {
              width: 350px;
              height: 350px;
              position: absolute;
              left: 70%;
              top: 63%;
              transform: translate(-50%, -50%);
            }

            .fade-in {
              opacity: 0;
            }
          }
        }
      `}</style>
    </>
  );
}
