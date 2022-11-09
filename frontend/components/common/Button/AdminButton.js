export function AdminButton({ context, color, width, height }) {
  return (
    <>
      <div className="btn">{context}</div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .btn {
            margin: 0 auto;
            width: ${width};
            height: ${height};
            border-radius: 7px;
            font-size: 13px;
            text-align: center;
            line-height: 21px;
            color: #fff;
            background-color: ${color};
            cursor: pointer;
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .btn {
            margin: 0 auto;
            width: ${width};
            height: ${height};
            border-radius: 7px;
            font-size: 13px;
            text-align: center;
            line-height: 21px;
            color: #fff;
            background-color: ${color};
            cursor: pointer;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .btn {
            margin: 0 auto;
            width: 40px;
            height: 20px;
            border-radius: 3px;
            font-size: 10px;
            text-align: center;
            line-height: 20px;
            color: #fff;
            background-color: ${color};
            cursor: pointer;
          }
        }
      `}</style>
    </>
  );
}

AdminButton.defaultProps = {
  width: "40px",
  height: "21px",
};
