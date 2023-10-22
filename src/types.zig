const net = @import("std").net;

pub const RequestVariant = enum {
    join_lobby,
    update,
};

pub const JoinLobbyRequest = struct { lobby_id: u8, player_nick: []const u8 };
pub const UpdateRequest = struct {
    lobby_id: u8,
    player_nick: []const u8,
    data: struct { score: u8, done_til: u8, max: u8 },
};

pub const ClientRequest = union(RequestVariant) {
    join_lobby: JoinLobbyRequest,
    update: UpdateRequest,
};

pub const Client = struct {
    stream: net.Stream,
    addr: net.Address,
    nick: []const u8,
    score: u8,
    done_til: u8,
};

pub const Response = struct {
    variant: enum {
        ok,
        err_invalid_data,
        err_bad_response,
    },
    msg: []const u8,
};
