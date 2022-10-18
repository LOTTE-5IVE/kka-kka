import YouTube from "react-youtube";

export default function Youtube() {
  const opts = {
    width: "100%",
    height: "700px",
    playerVars: {
      autoplay: 1,
    },
  };

  return (
    <>
      <section className="youtube">
        <div className="youtube__area">
          <div>
            <YouTube videoId="ARZsFfz-Saw" opts={opts} />
          </div>
        </div>
        <div className="youtube__cover"></div>
        <div className="inner"></div>
      </section>
      <style jsx>{`
        .youtube {
          position: relative;
          height: 700px;
          background-color: #333;
          overflow: hidden;
        }
        .youtube .youtube__area {
          position: absolute;
          width: 100%;
        }

        .youtube .youtube__cover {
          background-image: url("/images/video_cover_pattern.png");
          background-color: rgba(0, 0, 0, 0.3);
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }

        .youtube .inner {
          width: 1100px;
          margin: 0 auto;
          position: relative;
          height: 700px;
        }
      `}</style>
    </>
  );
}
