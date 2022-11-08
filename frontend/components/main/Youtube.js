import YouTube from "react-youtube";

export default function Youtube() {
  const opts = {
    width: "100%",
    height: "100%",
    playerVars: {
      autoplay: 1,
      loop: 1,
      mute: 1,
      playlist: "ARZsFfz-Saw",
    },
  };

  return (
    <>
      <section className="youtube">
        <div className="youtube__area">
          <YouTube
            className="video"
            videoId="ARZsFfz-Saw"
            opts={opts}
            onEnd={(e) => {
              e.target.stopVideo(0);
            }}
            style={{ height: "100%" }}
          />
        </div>

        <div className="youtube__cover"></div>
      </section>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .youtube {
            position: relative;
            width: 1903px;
            height: 700px;
            background-color: #333;
            overflow: hidden;

            .youtube__area {
              position: absolute;
              width: 1903px;
              height: 700px;
            }

            .youtube__cover {
              background-image: url("/main/video_cover_pattern.png");
              background-color: rgba(0, 0, 0, 0.3);
              position: absolute;
              top: 0;
              left: 0;
              width: 1903px;
              height: 700px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          .youtube {
            position: relative;
            width: 100vw;
            height: 36.84vw;
            background-color: #333;
            overflow: hidden;

            .youtube__area {
              position: absolute;
              width: 100vw;
              height: 36.84vw;
            }

            .youtube__cover {
              background-image: url("/main/video_cover_pattern.png");
              background-color: rgba(0, 0, 0, 0.3);
              position: absolute;
              top: 0;
              left: 0;
              width: 100vw;
              height: 36.84vw;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .youtube {
            position: relative;
            width: 480px;
            height: 400px;
            background-color: #333;
            overflow: hidden;

            .youtube__area {
              position: absolute;
              width: 480px;
              height: 400px;
            }

            .youtube__cover {
              background-image: url("/main/video_cover_pattern.png");
              background-color: rgba(0, 0, 0, 0.3);
              position: absolute;
              top: 0;
              left: 0;
              width: 480px;
              height: 400px;
            }
          }
        }
      `}</style>
    </>
  );
}
